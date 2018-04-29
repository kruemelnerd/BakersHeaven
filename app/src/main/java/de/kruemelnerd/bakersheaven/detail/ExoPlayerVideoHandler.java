package de.kruemelnerd.bakersheaven.detail;

import android.content.Context;
import android.net.Uri;
import android.view.SurfaceView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class ExoPlayerVideoHandler {
    private static ExoPlayerVideoHandler instance;

    public static ExoPlayerVideoHandler getInstance() {
        if (instance == null) {
            instance = new ExoPlayerVideoHandler();
        }
        return instance;
    }

    private SimpleExoPlayer player;
    private Uri playerUri;
    private boolean isPlayerPlaying;

    /**
     * use getInstance()
     */
    private ExoPlayerVideoHandler() {
    }


    public void prepareExoPlayerForUri(Context context, Uri uri, PlayerView exoPlayerView) {
        if (context != null && uri != null && exoPlayerView != null) {
            if (!uri.equals(playerUri) || player == null) {
                // Create a new player if the player is null or
                // we want to play a new video
                playerUri = uri;

                TrackSelector trackSelector = new DefaultTrackSelector();
                LoadControl loadControl = new DefaultLoadControl();

                player = ExoPlayerFactory.newSimpleInstance(context, trackSelector, loadControl);
                exoPlayerView.setPlayer(player);

                String userAgent = Util.getUserAgent(context, "bakersheaven");
                MediaSource mediaSource = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(
                        context, userAgent), new DefaultExtractorsFactory(), null, null);
                // Prepare the player with the source.
                player.prepare(mediaSource);
                player.setPlayWhenReady(true);

            }
            player.clearVideoSurface();
            player.setVideoSurfaceView(
                    (SurfaceView) exoPlayerView.getVideoSurfaceView());
            player.seekTo(player.getCurrentPosition() + 1);
            exoPlayerView.setPlayer(player);
            isPlayerPlaying = true;
        }
    }

    public void releaseVideoPlayer() {
        if (player != null) {
            player.release();
        }
        player = null;
    }

    public void goToBackground() {
        if (player != null) {
            isPlayerPlaying = player.getPlayWhenReady();
            player.setPlayWhenReady(false);
        }
    }

    public void goToForeground() {
        if (player != null) {
            player.setPlayWhenReady(isPlayerPlaying);
        }
    }

}
