package com.example.meditation.view.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.meditation.R
import com.example.meditation.util.FragmentTag
import com.example.meditation.util.PlayStatus
import com.example.meditation.view.dialog.LevelSelectDialog
import com.example.meditation.view.dialog.ThemeSelectedDialog
import com.example.meditation.view.dialog.TimeSelectDialog
import com.example.meditation.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Meditation)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeViewModel()

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

    private fun observeViewModel() {
        mainViewModel.playStatus.observe(this, Observer { status ->
            when(status) {
                PlayStatus.BEFORE_START -> {
                    bottom_navi.visibility = View.VISIBLE
                }
                PlayStatus.ON_START -> {
                    bottom_navi.visibility = View.INVISIBLE
                }
                PlayStatus.RUNNING -> {
                    bottom_navi.visibility = View.INVISIBLE
                }
                PlayStatus.PAUSE -> {

                }
                PlayStatus.END -> {

                }
            }
        })
    }
}