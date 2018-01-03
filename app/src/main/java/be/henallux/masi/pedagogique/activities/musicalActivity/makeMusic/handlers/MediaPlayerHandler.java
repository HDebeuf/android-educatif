package be.henallux.masi.pedagogique.activities.musicalActivity.makeMusic.handlers;

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
 *
 * * Copyright 2017 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public final class MediaPlayerHandler implements IMediaPlayerHandler {

    public static final int PLAYBACK_POSITION_REFRESH_INTERVAL_MS = 1000;

    private final Context mContext;
    private MediaPlayer mMediaPlayer;
    private Uri resourceUri;
    private PlaybackInfoListener mPlaybackInfoListener;
    private ScheduledExecutorService mExecutor;
    private Runnable mSeekbarPositionUpdateTask;

    private int currentPosition;
    private int duration;

    public MediaPlayerHandler(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * Once the {@link MediaPlayer} is released, it can't be used again, and another one has to be
     * created. In the onStop() method of the {@link MainActivity} the {@link MediaPlayer} is
     * released. Then in the onStart() of the {@link MainActivity} a new {@link MediaPlayer}
     * object has to be created. That's why this method is private, and called by load(int) and
     * not the constructor.
     */
    private void initializeMediaPlayer() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    stopUpdatingCallbackWithPosition(true);
                    logToUI("MediaPlayer playback completed");
                    if (mPlaybackInfoListener != null) {
                        mPlaybackInfoListener.onStateChanged(PlaybackInfoListener.State.COMPLETED);
                        mPlaybackInfoListener.onPlaybackCompleted();
                    }
                }
            });
            logToUI("mMediaPlayer = new MediaPlayer()");
        }
    }

    public void setPlaybackInfoListener(PlaybackInfoListener listener) {
        mPlaybackInfoListener = listener;
    }

    // Implements PlaybackControl.
    @Override
    public void loadMedia(Uri resourceUri) {
        this.resourceUri = resourceUri;

        initializeMediaPlayer();


        try {
            logToUI("load() {1. setDataSource}");
            mMediaPlayer.setDataSource(mContext, resourceUri);
        } catch (Exception e) {
            logToUI(e.toString());
        }

        try {
            logToUI("load() {2. prepare}");
            mMediaPlayer.prepare();
        } catch (Exception e) {
            logToUI(e.toString());
        }

        initializeProgressCallback();
        logToUI("initializeProgressCallback()");
    }

    @Override
    public void release() {
        if (mMediaPlayer != null) {
            logToUI("release() and mMediaPlayer = null");
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public boolean isPlaying() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.isPlaying();
        }
        return false;
    }

    @Override
    public void play() {
        if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {

            mMediaPlayer.start();
            if (mPlaybackInfoListener != null) {
                mPlaybackInfoListener.onStateChanged(PlaybackInfoListener.State.PLAYING);
            }
            startUpdatingCallbackWithPosition();
        }
    }

    @Override
    public void reset() {
        if (mMediaPlayer != null) {
            logToUI("playbackReset()");
            mMediaPlayer.reset();
            loadMedia(resourceUri);
            if (mPlaybackInfoListener != null) {
                mPlaybackInfoListener.onStateChanged(PlaybackInfoListener.State.RESET);
            }
            stopUpdatingCallbackWithPosition(true);
        }
    }

    @Override
    public void pause() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            if (mPlaybackInfoListener != null) {
                mPlaybackInfoListener.onStateChanged(PlaybackInfoListener.State.PAUSED);
            }
            logToUI("playbackPause()");
        }
    }

    @Override
    public void seekTo(int position) {
        if (mMediaPlayer != null) {
            logToUI(String.format("seekTo() %d ms", position));
            mMediaPlayer.seekTo(position);
        }
    }

    /**
     * Syncs the mMediaPlayer position with mPlaybackProgressCallback via recurring task.
     */

    private void startUpdatingCallbackWithPosition() {
        if (mExecutor == null) {
            mExecutor = Executors.newSingleThreadScheduledExecutor();
        }
        if (mSeekbarPositionUpdateTask == null) {
            mSeekbarPositionUpdateTask = new Runnable() {
                @Override
                public void run() {
                    Log.d("SeekToThread","running");
                    updateProgressCallbackTask();
                }
            };
        }
        mExecutor.scheduleAtFixedRate(
                mSeekbarPositionUpdateTask,
                0,
                PLAYBACK_POSITION_REFRESH_INTERVAL_MS,
                TimeUnit.MILLISECONDS
        );
    }

    // Reports media playback position to mPlaybackProgressCallback.
    private void stopUpdatingCallbackWithPosition(boolean resetUIPlaybackPosition) {
        if (mExecutor != null) {
            mExecutor.shutdownNow();
            mExecutor = null;
            mSeekbarPositionUpdateTask = null;
            if (resetUIPlaybackPosition && mPlaybackInfoListener != null) {
                mPlaybackInfoListener.onPositionChanged(0);
            }
        }
    }

    private void updateProgressCallbackTask() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            currentPosition = mMediaPlayer.getCurrentPosition();
            Log.d("SeekToThread", String.valueOf(currentPosition));
            if (mPlaybackInfoListener != null) {
                mPlaybackInfoListener.onPositionChanged(currentPosition);
            }
        }
    }

    @Override
    public void initializeProgressCallback() {
        duration = mMediaPlayer.getDuration();
        if (mPlaybackInfoListener != null) {
            mPlaybackInfoListener.onDurationChanged(duration);
            mPlaybackInfoListener.onPositionChanged(0);
            logToUI(String.format("firing setPlaybackDuration(%d sec)",
                    TimeUnit.MILLISECONDS.toSeconds(duration)));
            logToUI("firing setPlaybackPosition(0)");
        }
    }

    private void logToUI(String message) {
        if (mPlaybackInfoListener != null) {
            mPlaybackInfoListener.onLogUpdated(message);
        }
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public int getDuration() {
        return duration;
    }
}
