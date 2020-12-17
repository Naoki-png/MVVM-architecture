package com.example.meditation.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.meditation.R
import com.example.meditation.util.FragmentTag
import com.example.meditation.view.dialog.LevelSelectDialog
import com.example.meditation.view.dialog.ThemeSelectedDialog
import com.example.meditation.view.dialog.TimeSelectDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Meditation)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.screen_container, MainFragment())
                    .commit()
        }

        bottom_navi.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_select_level -> {
                    LevelSelectDialog().show(supportFragmentManager, FragmentTag.LEVEL_SELECT.name)
                    true
                }
                R.id.item_select_theme -> {
                    ThemeSelectedDialog().show(supportFragmentManager, FragmentTag.THEME_SELECT.name)
                    true
                }
                R.id.item_select_time -> {
                    TimeSelectDialog().show(supportFragmentManager, FragmentTag.TIME_SELECT.name)
                    true
                }
                else -> false
            }
        }
    }
}