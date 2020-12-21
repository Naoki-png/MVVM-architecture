package com.example.meditation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meditation.R
import com.example.meditation.data.ThemeData
import com.example.meditation.model.UserSettings
import com.example.meditation.model.UserSettingsRepository
import com.example.meditation.util.PlayStatus
import java.util.*
import kotlin.concurrent.schedule

class MainViewModel(val context: Application): AndroidViewModel(context) {
    var msgUpperSmall = MutableLiveData<String>()
    var msgLowerLarge = MutableLiveData<String>()
    var themePicFileResId = MutableLiveData<Int>()
    var txtTheme = MutableLiveData<String>()
    var txtLevel = MutableLiveData<String>()

    var remainedTimeSeconds = MutableLiveData<Int>()
    var displayTimeSeconds = MutableLiveData<String>()

    var playStatus = MutableLiveData<Int>()

    private val userSettingsRepository = UserSettingsRepository()
    private lateinit var userSettings: UserSettings

    //呼吸間隔
    private val inhaleInterval = 4
    private var holdInterval = 0
    private var exhaleInterval = 0
    private var totalInterval = 0
    fun initParameters() {
        userSettings = userSettingsRepository.loadUserSettings()
        msgUpperSmall.value = ""
        msgLowerLarge.value = ""
        themePicFileResId.value = userSettings.themeResId
        txtTheme.value = userSettings.themeName
        txtLevel.value = userSettings.levelName
        remainedTimeSeconds.value = userSettings.time * 60
        displayTimeSeconds.value = changeTimeFormat(remainedTimeSeconds.value!!)
        playStatus.value = PlayStatus.BEFORE_START
    }

    private fun changeTimeFormat(timeSeconds: Int): String? {
        val minutes = timeSeconds / 60
        val minutesString = if (minutes < 10) "0" + minutes.toString() else minutes.toString()
        val seconds = timeSeconds - (minutes * 60)
        val secondsString = if (seconds < 10) "0" + seconds.toString() else seconds.toString()
        return minutesString + " : " + secondsString
    }

    fun setLevel(selectedItemId: Int) {
        txtLevel.value = userSettingsRepository.setLevel(selectedItemId)
    }

    fun setTime(selectedItemId: Int) {
        remainedTimeSeconds.value = userSettingsRepository.setTime(selectedItemId) * 60
        displayTimeSeconds.value = changeTimeFormat(remainedTimeSeconds.value!!)
    }

    fun setTheme(themeData: ThemeData) {
        userSettingsRepository.setTheme(themeData)
        txtTheme.value = userSettingsRepository.loadUserSettings().themeName
        themePicFileResId.value = userSettingsRepository.loadUserSettings().themeResId
    }

    fun changeStatus() {
        val status = playStatus.value
        when (status) {
            PlayStatus.BEFORE_START -> playStatus.value = PlayStatus.ON_START
            PlayStatus.RUNNING -> playStatus.value = PlayStatus.PAUSE
            PlayStatus.PAUSE -> playStatus.value = PlayStatus.RUNNING
        }
    }

    fun countDownBeforeStart() {
        msgUpperSmall.value = context.resources.getString(R.string.starts_in)
        var timeRemained = 3
        msgLowerLarge.value = timeRemained.toString()
        val timer = Timer()
        timer.schedule(1000, 1000) {
            if (timeRemained > 1) {
                timeRemained -= 1
                msgLowerLarge.postValue(timeRemained.toString())
            } else {
                playStatus.postValue(PlayStatus.RUNNING)
                timeRemained = 0
                timer.cancel()
            }
        }
    }

    fun startMeditation() {
        holdInterval = setHoldInterval()
        exhaleInterval = setExhaleInterval()
        totalInterval = inhaleInterval + holdInterval + exhaleInterval

        remainedTimeSeconds.value = adjustRemainedTime(remainedTimeSeconds.value, totalInterval)
        displayTimeSeconds.value = changeTimeFormat(remainedTimeSeconds.value!!)

        msgUpperSmall.value = context.resources.getString(R.string.inhale)
        msgLowerLarge.value = inhaleInterval.toString()
    }

    private fun adjustRemainedTime(remainedTime: Int?, totalInterval: Int): Int? {
        val remainder = remainedTime!! % totalInterval
        return if (remainder > (totalInterval / 2)) {
            remainedTime + (totalInterval - remainder)
        } else {
            remainedTime - remainder
        }
    }

    private fun setExhaleInterval(): Int {
        val levelId = userSettingsRepository.loadUserSettings().levelId
        return when(levelId) {
            0 -> 4
            1 -> 8
            2 -> 8
            3 -> 8
            else -> 0
        }
    }

    private fun setHoldInterval(): Int {
        val levelId = userSettingsRepository.loadUserSettings().levelId
        return when(levelId) {
            0 -> 4
            1 -> 4
            2 -> 8
            3 -> 16
            else -> 0
        }
    }
}