package be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by hendrikdebeuf2 on 31/12/17.
 */

public class MediaPlayerAdapter implements IMediaPlayerAdapter {

    public static final int PLAYBACK_POSITION_REFRESH_INTERVAL_MS = 1000;

    private final Context context;
    private MediaPlayer mediaPlayer;
    private Uri sampleUri;
    private PlaybackInfoListener playbackInfoListener;
    private ScheduledExecutorService executor;
    private Runnable seekbarPositionUpdateTask;

    public MediaPlayerAdapter(Context context) {
        this.context = context.getApplicationContext();
    }

    private void initializeMediaPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopUpdatingCallbackWithPosition(true);
                    Log.d("mediainfo","MediaPlayer playback completed");
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
    public void loadMedia(Uri sampleUri) {
        this.sampleUri = sampleUri;

        initializeMediaPlayer();

        try {
            Log.d("mediainfo", "load() {1. setDataSource}");
            mediaPlayer.setDataSource(context, sampleUri);
        } catch (Exception e) {
            Log.d("mediainfo",e.toString());
        }

        try {
            Log.d("mediainfo", "load() {2. prepare}");
            mediaPlayer.prepare();
        } catch (Exception e) {
            Log.d("mediainfo", e.toString());
        }

        initializeProgressCallback();
        Log.d("mediainfo", "initializeProgressCallback()");
    }

    @Override
    public void release() {
        if (mediaPlayer != null) {
            Log.d("mediainfo", "release() and mediaPlayer = null");
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
            Log.d("mediainfo", String.format("playbackStart() %s", sampleUri));
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
            Log.d("mediainfo","playbackReset()");
            mediaPlayer.reset();
            loadMedia(sampleUri);
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
            Log.d("mediainfo","playbackPause()");
        }
    }

    @Override
    public void seekTo(int position) {
        if (mediaPlayer != null) {
            Log.d("mediainfo",String.format("seekTo() %d ms", position));
            mediaPlayer.seekTo(position);
        }
    }

    /**
     * Syncs the mediaPlayer position with playbackProgressCallback via recurring task.
     */
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
            int currentPosition = mediaPlayer.getCurrentPosition();
            if (playbackInfoListener != null) {
                playbackInfoListener.onPositionChanged(currentPosition);
            }
        }
    }

    @Override
    public void initializeProgressCallback() {
        final int duration = mediaPlayer.getDuration();
        if (playbackInfoListener != null) {
            playbackInfoListener.onDurationChanged(duration);
            playbackInfoListener.onPositionChanged(0);
            Log.d("mediainfo",String.format("firing setPlaybackDuration(%d sec)",
                    TimeUnit.MILLISECONDS.toSeconds(duration)));
            Log.d("mediainfo","firing setPlaybackPosition(0)");
        }
    }

}
