package com.example.meditation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meditation.data.ThemeData
import com.example.meditation.model.UserSettings
import com.example.meditation.model.UserSettingsRepository
import com.example.meditation.util.PlayStatus

class MainViewModel: ViewModel() {
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
}