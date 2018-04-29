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

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang.StringUtils;

import de.kruemelnerd.bakersheaven.R;
import de.kruemelnerd.bakersheaven.data.StepsItem;
import de.kruemelnerd.bakersheaven.util.StepUtil;


public class DetailInformationFragment extends Fragment {
    public static final String EXTRA_STEP = "extra_step";

    private View mView;
    private StepsItem mStep;
    //private static MediaSessionCompat mMediaSession;
    private SimpleExoPlayer mExoPlayer;
    private PlayerView mPlayerView;


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
        String videoPath = mStep.getVideoURL();

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


            // Initialize the player.
            initializePlayer(Uri.parse(videoPath));
        }


        return mView;
    }

    private void initializePlayer(Uri mediaUri) {
        if(mediaUri != null && mPlayerView != null){
            mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource(getResources(), R.drawable.ic_chef_round));
            ExoPlayerVideoHandler.getInstance().prepareExoPlayerForUri(mView.getContext(), mediaUri, mPlayerView);
            ExoPlayerVideoHandler.getInstance().goToForeground();
        }
    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(EXTRA_STEP, mStep);
        super.onSaveInstanceState(outState);
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
    }

    /**
     * Release the player when the activity is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        //mMediaSession.setActive(false);
    }


}
