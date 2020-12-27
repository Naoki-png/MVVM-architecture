package com.example.meditation.view.main


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.meditation.R
import com.example.meditation.service.MusicServiceHelper
import com.example.meditation.util.FragmentTag
import com.example.meditation.util.NotificationHelper
import com.example.meditation.util.PlayStatus
import com.example.meditation.view.dialog.LevelSelectDialog
import com.example.meditation.view.dialog.ThemeSelectedDialog
import com.example.meditation.view.dialog.TimeSelectDialog
import com.example.meditation.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    private val musicServiceHelper: MusicServiceHelper by inject()
    private val notificationHelper: NotificationHelper by inject()

    private val mainFragment: MainFragment by inject()
    private val levelSelectDialog: LevelSelectDialog by inject()
    private val themeSelectedDialog: ThemeSelectedDialog by inject()
    private val timeSelectDialog: TimeSelectDialog by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Meditation)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeViewModel()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.screen_container, mainFragment)
                    .commit()
        }

        bottom_navi.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item_select_level -> {
                    levelSelectDialog.show(supportFragmentManager, FragmentTag.LEVEL_SELECT.name)
                    true
                }
                R.id.item_select_theme -> {
                    themeSelectedDialog.show(supportFragmentManager, FragmentTag.THEME_SELECT.name)
                    true
                }
                R.id.item_select_time -> {
                    timeSelectDialog.show(supportFragmentManager, FragmentTag.TIME_SELECT.name)
                    true
                }
                else -> false
            }
        }

        musicServiceHelper.bindService()
    }

    override fun onResume() {
        super.onResume()
        notificationHelper.stopNotification()
    }

    override fun onPause() {
        super.onPause()
        notificationHelper.startNotification()
    }

    override fun onDestroy() {
        super.onDestroy()
        notificationHelper.stopNotification()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        musicServiceHelper.stopBgm()
        finish()
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
                    musicServiceHelper.startBgm()
                }
                PlayStatus.PAUSE -> {
                    bottom_navi.visibility = View.INVISIBLE
                    musicServiceHelper.stopBgm()
                }
                PlayStatus.END -> {
                    bottom_navi.visibility = View.VISIBLE
                    musicServiceHelper.stopBgm()
                    musicServiceHelper.ringFinalGong()
                }
            }
        })

        mainViewModel.volume.observe(this, Observer { volume ->
            musicServiceHelper.setVolume(volume)
        })
    }
}