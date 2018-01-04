package be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by hendrikdebeuf2 on 31/12/17.
 *
 * source: https://github.com/googlesamples/android-SimpleMediaPlayer
 */

public final class MediaPlayerHandler implements IMediaPlayerHandler {

    private static final int PLAYBACK_POSITION_REFRESH_INTERVAL_MS = 1000;

    private final Context context;
    private MediaPlayer mediaPlayer;
    private Uri resourceUri;
    private PlaybackInfoListener playbackInfoListener;
    private ScheduledExecutorService executor;
    private Runnable seekbarPositionUpdateTask;

    private int currentPosition;
    private int duration;

    public MediaPlayerHandler(Context context) {
        this.context = context.getApplicationContext();
    }

    private void initializeMediaPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopUpdatingCallbackWithPosition(true);
                    if (playbackInfoListener != null) {
                        playbackInfoListener.onStateChanged(PlaybackInfoListener.State.COMPLETED);
                        playbackInfoListener.onPlaybackCompleted();
                    }
                }
            });
        }
    }

    public void setPlaybackInfoListener(PlaybackInfoListener listener) {
        playbackInfoListener = listener;
    }

    // Implements PlaybackControl.
    @Override
    public void loadMedia(Uri resourceUri) {
        this.resourceUri = resourceUri;

        initializeMediaPlayer();


        try {
            mediaPlayer.setDataSource(context, resourceUri);
        } catch (Exception e) {
            Log.d("MediaPlayer", e.toString());
        }

        try {
            mediaPlayer.prepare();
        } catch (Exception e) {
            Log.d("MediaPlayer", e.toString());
        }

        initializeProgressCallback();
    }

    @Override
    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public boolean isPlaying() {
        if (mediaPlayer != null) {
            return mediaPlayer.isPlaying();
        }
        return false;
    }

    @Override
    public void play() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {

            mediaPlayer.start();
            if (playbackInfoListener != null) {
                playbackInfoListener.onStateChanged(PlaybackInfoListener.State.PLAYING);
            }
            startUpdatingCallbackWithPosition();
        }
    }

    @Override
    public void reset() {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            loadMedia(resourceUri);
            if (playbackInfoListener != null) {
                playbackInfoListener.onStateChanged(PlaybackInfoListener.State.RESET);
            }
            stopUpdatingCallbackWithPosition(true);
        }
    }

    @Override
    public void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            if (playbackInfoListener != null) {
                playbackInfoListener.onStateChanged(PlaybackInfoListener.State.PAUSED);
            }
        }
    }

    @Override
    public void seekTo(int position) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(position);
        }
    }

    private void startUpdatingCallbackWithPosition() {
        if (executor == null) {
            executor = Executors.newSingleThreadScheduledExecutor();
        }
        if (seekbarPositionUpdateTask == null) {
            seekbarPositionUpdateTask = new Runnable() {
                @Override
                public void run() {
                    updateProgressCallbackTask();
                }
            };
        }
        executor.scheduleAtFixedRate(
                seekbarPositionUpdateTask,
                0,
                PLAYBACK_POSITION_REFRESH_INTERVAL_MS,
                TimeUnit.MILLISECONDS
        );
    }

    // Reports media playback position to mPlaybackProgressCallback.
    private void stopUpdatingCallbackWithPosition(boolean resetUIPlaybackPosition) {
        if (executor != null) {
            executor.shutdownNow();
            executor = null;
            seekbarPositionUpdateTask = null;
            if (resetUIPlaybackPosition && playbackInfoListener != null) {
                playbackInfoListener.onPositionChanged(0);
            }
        }
    }

    private void updateProgressCallbackTask() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            currentPosition = mediaPlayer.getCurrentPosition();
            if (playbackInfoListener != null) {
                playbackInfoListener.onPositionChanged(currentPosition);
            }
        }
    }

    @Override
    public void initializeProgressCallback() {
        duration = mediaPlayer.getDuration();
        if (playbackInfoListener != null) {
            playbackInfoListener.onDurationChanged(duration);
            playbackInfoListener.onPositionChanged(0);
        }
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public int getDuration() {
        return duration;
    }
}
