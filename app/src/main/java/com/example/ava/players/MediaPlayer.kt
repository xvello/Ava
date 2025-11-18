package com.example.ava.players

import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

class MediaPlayer(private val player: ExoPlayer) : AutoCloseable {

    val isPlaying get() = player.isPlaying
    val isPaused get() = !player.isPlaying && player.playbackState != Player.STATE_IDLE && player.playbackState != Player.STATE_ENDED
    val isStopped get() = player.playbackState == Player.STATE_IDLE || player.playbackState == Player.STATE_ENDED

    var volume
        get() = player.volume
        set(value) {
            player.volume = value
        }

    fun play(vararg mediaUris: String) {
        runCatching {
            player.clearMediaItems()
            for (mediaUri in mediaUris) {
                player.addMediaItem(MediaItem.fromUri(mediaUri))
            }
            player.playWhenReady = true
            player.prepare()
        }.onFailure {
            Log.e(TAG, "Error playing media $mediaUris", it)
        }
    }

    fun pause() {
        if (isPlaying) {
            player.pause()
        }
    }

    fun unpause() {
        if (isPaused)
            player.play()
    }

    fun stop() {
        player.stop()
    }

    fun addListener(listener: Player.Listener) {
        player.addListener(listener)
    }

    fun removeListener(listener: Player.Listener) {
        player.removeListener(listener)
    }

    override fun close() {
        player.release()
    }

    companion object {
        private const val TAG = "AudioPlayer"
    }
}