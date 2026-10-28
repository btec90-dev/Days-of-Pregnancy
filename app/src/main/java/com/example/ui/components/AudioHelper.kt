package com.example.ui.components

import android.content.Context
import android.media.AudioFormat
import android.media.AudioAttributes
import android.media.AudioTrack
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import java.util.Locale
import kotlin.math.sin

class AudioHelper(context: Context) : TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = TextToSpeech(context, this)
    private var isInitialized = false
    private var audioTrack: AudioTrack? = null
    private var isDopplerRunning = false
    private var dopplerThread: Thread? = null

    init {
        tts = TextToSpeech(context, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.FRANCE)
            if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
                isInitialized = true
            }
        }
    }

    fun speak(text: String, onStart: () -> Unit = {}, onDone: () -> Unit = {}) {
        if (!isInitialized) {
            onDone()
            return
        }
        val params = HashMap<String, String>()
        params[TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID] = "narration_utterance"

        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                onStart()
            }
            override fun onDone(utteranceId: String?) {
                onDone()
            }
            override fun onError(utteranceId: String?) {
                onDone()
            }
        })

        // Set pitch slightly lower for calm warm voice, rate 0.85
        tts?.setPitch(0.92f)
        tts?.setSpeechRate(0.85f)
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, params)
    }

    fun stopSpeaking() {
        tts?.stop()
    }

    fun toggleDoppler(bpm: Int, onStateChanged: (Boolean) -> Unit) {
        if (isDopplerRunning) {
            stopDoppler()
            onStateChanged(false)
        } else {
            startDoppler(bpm)
            onStateChanged(true)
        }
    }

    private fun startDoppler(bpm: Int) {
        isDopplerRunning = true
        dopplerThread = Thread {
            val sampleRate = 22050
            val intervalMs = (60.0 / bpm * 1000).toInt()
            val bufferSize = AudioTrack.getMinBufferSize(
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            )

            val track = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(bufferSize)
                .setTransferMode(AudioTrack.MODE_STREAM)
                .build()

            audioTrack = track
            track.play()

            val soundDurationSec1 = 0.09
            val soundDurationSec2 = 0.08
            val numSamples1 = (sampleRate * soundDurationSec1).toInt()
            val numSamples2 = (sampleRate * soundDurationSec2).toInt()
            val pcm1 = generateHeartbeatBuffer(sampleRate, numSamples1, 90.0)
            val pcm2 = generateHeartbeatBuffer(sampleRate, numSamples2, 60.0)

            while (isDopplerRunning) {
                try {
                    track.write(pcm1, 0, pcm1.size)
                    Thread.sleep(110)
                    track.write(pcm2, 0, pcm2.size)
                    val sleepTime = maxOf(50, intervalMs - 220)
                    Thread.sleep(sleepTime.toLong())
                } catch (e: InterruptedException) {
                    break
                }
            }
            track.stop()
            track.release()
            audioTrack = null
        }
        dopplerThread?.start()
    }

    private fun generateHeartbeatBuffer(sampleRate: Int, numSamples: Int, freq: Double): ShortArray {
        val buffer = ShortArray(numSamples)
        for (i in 0 until numSamples) {
            val t = i.toDouble() / sampleRate
            val envelope = kotlin.math.exp(-t * 22.0)
            val valSample = sin(2.0 * Math.PI * freq * t) * envelope * 24000.0
            buffer[i] = valSample.toInt().toShort()
        }
        return buffer
    }

    fun stopDoppler() {
        isDopplerRunning = false
        dopplerThread?.interrupt()
        dopplerThread = null
    }

    fun shutdown() {
        stopSpeaking()
        stopDoppler()
        tts?.shutdown()
    }
}
