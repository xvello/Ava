package com.example.ava.esphome.voicesatellite

import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import com.example.ava.players.AudioPlayer
import com.example.ava.players.TtsPlayer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@OptIn(UnstableApi::class)
class VoiceSatellitePlayer(
    val ttsPlayer: TtsPlayer,
    val mediaPlayer: AudioPlayer,
    private val duckMultiplier: Float = 0.5f
) : AutoCloseable {
    private var _isDucked = false
    private val _volume = MutableStateFlow(1.0f)
    private val _muted = MutableStateFlow(false)

    val volume get() = _volume.asStateFlow()
    fun setVolume(value: Float) {
        _volume.value = value
        if (!_muted.value) {
            ttsPlayer.volume = value
            mediaPlayer.volume = if (_isDucked) value * duckMultiplier else value
        }
    }

    val muted get() = _muted.asStateFlow()
    fun setMuted(value: Boolean) {
        _muted.value = value
        if (value) {
            mediaPlayer.volume = 0.0f
            ttsPlayer.volume = 0.0f
        } else {
            ttsPlayer.volume = _volume.value
            mediaPlayer.volume = if (_isDucked) _volume.value * duckMultiplier else _volume.value
        }
    }

    fun duck() {
        _isDucked = true
        if (!_muted.value) {
            mediaPlayer.volume = _volume.value * duckMultiplier
        }
    }

    fun unDuck() {
        _isDucked = false
        if (!_muted.value) {
            mediaPlayer.volume = _volume.value
        }
    }

    override fun close() {
        ttsPlayer.close()
        mediaPlayer.close()
    }
}