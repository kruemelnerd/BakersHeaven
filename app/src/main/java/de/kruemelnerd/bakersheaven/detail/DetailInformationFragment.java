package de.kruemelnerd.bakersheaven.detail;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.PlayerView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang.StringUtils;

import de.kruemelnerd.bakersheaven.R;
import de.kruemelnerd.bakersheaven.data.StepsItem;
import de.kruemelnerd.bakersheaven.util.StepUtil;


public class DetailInformationFragment extends Fragment {
    public static final String EXTRA_STEP = "extra_step";
    private static final String PLAYBACK_POSITION = "playback_position";
    private static final String VIDEO_PLAYSTATE= "video_playstate";
    private static final String VIDEO_PATH = "video_path";

    private View mView;
    private StepsItem mStep;
    private PlayerView mPlayerView;
    private static String videoPath;
    private long playbackPosition;
    private boolean isVideoPlaying;

    public DetailInformationFragment() {
    }






    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(EXTRA_STEP)) {
            mStep = getArguments().getParcelable(EXTRA_STEP);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (savedInstanceState != null) {
            mStep = savedInstanceState.getParcelable(EXTRA_STEP);
        }

        mView = inflater.inflate(R.layout.detail_master_fragment, container, false);


        final TextView stepInstruction = mView.findViewById(R.id.detail_step_instruction);
        stepInstruction.setText(mStep.getDescription());

        final ImageView stepImage = mView.findViewById(R.id.detail_step_image);
        mPlayerView = mView.findViewById(R.id.detail_step_video);


        String instructions = mStep.getDescription();
        String thumbnailPath = mStep.getThumbnailURL();
        videoPath = mStep.getVideoURL();

        instructions = StepUtil.removeFirstNumber(instructions);
        stepInstruction.setText(instructions);


        if (StringUtils.isBlank(videoPath)) {
            stepImage.setVisibility(View.VISIBLE);
            mPlayerView.setVisibility(View.GONE);
            if (StringUtils.isBlank(thumbnailPath)) {
                thumbnailPath = "isEmpty";
            }
            Picasso
                    .with(mView.getContext())
                    .load(thumbnailPath)
                    .fit()
                    .error(R.drawable.ic_chef_round)
                    .into(stepImage);
        } else {
            stepImage.setVisibility(View.GONE);
            mPlayerView.setVisibility(View.VISIBLE);

            playbackPosition = 0;
            isVideoPlaying = true;

            // if we have saved player state, restore it
            if (savedInstanceState != null) {
                playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION, 0);
                isVideoPlaying = savedInstanceState.getBoolean(VIDEO_PLAYSTATE, true);
            }

            // Initialize the player.
            initializePlayer(Uri.parse(videoPath));
        }


        return mView;
    }

    private void initializePlayer(Uri mediaUri) {
        if(mediaUri != null && mPlayerView != null){
            mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.ic_chef_round));
            ExoPlayerVideoHandler.getInstance().setPlaybackPosition(playbackPosition);
            ExoPlayerVideoHandler.getInstance().setPlayerPlaying(isVideoPlaying);
            ExoPlayerVideoHandler.getInstance().prepareExoPlayerForUri(mView.getContext(), mediaUri, mPlayerView);
            ExoPlayerVideoHandler.getInstance().goToForeground();
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(EXTRA_STEP, mStep);
        outState.putLong(PLAYBACK_POSITION, ExoPlayerVideoHandler.getInstance().getPlaybackPosition());
        outState.putBoolean(VIDEO_PLAYSTATE, ExoPlayerVideoHandler.getInstance().isPlayerPlaying());
        outState.putString(VIDEO_PATH, videoPath);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null){
            mStep = savedInstanceState.getParcelable(EXTRA_STEP);
            playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION);
            isVideoPlaying = savedInstanceState.getBoolean(VIDEO_PLAYSTATE);
            videoPath = savedInstanceState.getString(VIDEO_PATH);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // Initialize the player.
        initializePlayer(Uri.parse(videoPath));
    }

    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {

        ExoPlayerVideoHandler.getInstance().releaseVideoPlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        ExoPlayerVideoHandler.getInstance().goToBackground();
        releasePlayer();

    }

}
