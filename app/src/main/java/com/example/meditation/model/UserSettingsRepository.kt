package com.example.meditation.model

import android.content.Context
import com.example.meditation.MyApplication
import com.example.meditation.R
import com.example.meditation.data.ThemeData
import com.example.meditation.util.LevelId

class UserSettingsRepository {

    private val context = MyApplication.appContext
    private val pref = context.getSharedPreferences(
            UserSettings.PREF_USERSETTINGS_NAME, Context.MODE_PRIVATE
    )
    private val prefEditor = pref.edit()

    fun loadUserSettings(): UserSettings {
        return UserSettings(
                levelId = pref.getInt(UserSettingsPrefKey.LEVEL_ID.name, LevelId.EASY),
                levelName = context.getString(
                        pref.getInt(UserSettingsPrefKey.LEVEL_NAME_STR_ID.name, R.string.level_easy_header)),
                themeId = pref.getInt(UserSettingsPrefKey.THEME_ID.name, 0),
                themeName = context.getString(
                        pref.getInt(UserSettingsPrefKey.THEME_NAME_STR_ID.name, R.string.theme_silent)),
                themeResId = pref.getInt(UserSettingsPrefKey.THEME_RES_ID.name, R.drawable.pic_nobgm),
                themeSoundId = pref.getInt(UserSettingsPrefKey.THEME_SOUND_ID.name, 0),
                time = pref.getInt(UserSettingsPrefKey.TIME.name, 30)
        )
    }

    fun setLevel(selectedItemId: Int): String {
        prefEditor.putInt(UserSettingsPrefKey.LEVEL_ID.name, selectedItemId).commit()
        val levelNameStrId = when (selectedItemId) {
            0 -> R.string.level_easy_header
            1 -> R.string.level_normal_header
            2 -> R.string.level_mid_header
            3 -> R.string.level_high_header
            else -> 0
        }
        prefEditor.putInt(UserSettingsPrefKey.LEVEL_NAME_STR_ID.name, levelNameStrId).commit()
        return loadUserSettings().levelName
    }

    fun setTime(selectedItemId: Int): Int {
        val selecedTime = when (selectedItemId) {
            0 -> 5
            1 -> 10
            2 -> 15
            3 -> 20
            4 -> 30
            5 -> 45
            6 -> 60
            else -> 30
        }
        prefEditor.putInt(UserSettingsPrefKey.TIME.name, selecedTime).commit()
        return loadUserSettings().time
    }

    fun setTheme(themeData: ThemeData) {
        prefEditor.putInt(UserSettingsPrefKey.THEME_ID.name, themeData.themeId).commit()
        prefEditor.putInt(UserSettingsPrefKey.THEME_NAME_STR_ID.name, themeData.themeNameResId).commit()
        prefEditor.putInt(UserSettingsPrefKey.THEME_RES_ID.name, themeData.themeLandPicResId).commit()
        prefEditor.putInt(UserSettingsPrefKey.THEME_SOUND_ID.name, themeData.themeSoundId).commit()
    }
}