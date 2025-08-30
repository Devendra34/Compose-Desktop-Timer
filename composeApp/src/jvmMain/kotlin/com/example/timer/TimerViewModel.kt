package com.example.timer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.io.BufferedInputStream
import java.time.Duration
import java.util.concurrent.TimeUnit
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip

class TimerViewModel : ViewModel() {

    var hrs: Int = 0
    var mins: Int = 0
    var seconds: Int = 0

    var startTime: Long = 0

    var currTime: Long = 0L

    var isRunning by mutableStateOf(false)

    private val timerThread by lazy { Thread { st() } }

    val currentTimeFlow = MutableStateFlow<Long>(0)

    val remainingTime = currentTimeFlow.map {
        formatRemainingTime(it)
    }

    private var clip: Clip? = null

    fun formatRemainingTime(remainingTime: Long): String {
        // Ensure the time is treated as duration (milliseconds)
        val hours = (remainingTime / (1000 * 60 * 60))
        val minutes = (remainingTime / (1000 * 60)) % 60
        val seconds = (remainingTime / 1000) % 60

        // Format the time
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    var threadStarted = false
    var isStarted by mutableStateOf(false)

    fun start() {
        val startTime = Duration.ofHours(hrs.toLong()).toMillis() +
                Duration.ofMinutes(mins.toLong()).toMillis() +
                Duration.ofSeconds(seconds.toLong()).toMillis()
        if (startTime == 0L) return
        this.startTime = startTime

        updateCurrentTime(startTime, false)
        isRunning = true
        isStarted = true
        if (!threadStarted) {
            threadStarted = true
            timerThread.start()
        }
    }

    fun st() {
        while (true) {
            Thread.sleep(500)
            while (currTime > 0 && isRunning) {
                currTime -= 1000L.coerceAtMost(currTime)
                updateCurrentTime(currTime, true)
                Thread.sleep(1000)
            }
        }
    }

    fun onPlayPauseClick() {
        if (isStarted) resumeOrPause()
        else start()
    }

    fun resumeOrPause() {
        isRunning = !isRunning
    }

    fun stop() {
        updateCurrentTime(startTime, false)
        isRunning = false
        isStarted = false
        stopSound()
    }

    private fun stopSound() {
        try {
            clip?.stop()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun updateCurrentTime(time: Long, notify: Boolean) {
        currTime = time
        currentTimeFlow.value = time
        if (notify && currTime == 0L) {
            playSound("sounds/timer.wav")
        }
    }

    fun onTimeChange(value: Int, timeUnit: TimeUnit) {
        when (timeUnit) {
            TimeUnit.HOURS -> hrs = value
            TimeUnit.MINUTES -> mins = value
            TimeUnit.SECONDS -> seconds = value
            else -> {}
        }
    }

    fun playSound(resourcePath: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val audioStream = AudioSystem.getAudioInputStream(
                    BufferedInputStream(
                        Thread.currentThread().contextClassLoader.getResourceAsStream(resourcePath)!!
                    )
                )
                clip = AudioSystem.getClip()
                clip?.open(audioStream)
                clip?.start()
            } catch (e: Exception) {
                println("Error playing sound: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}