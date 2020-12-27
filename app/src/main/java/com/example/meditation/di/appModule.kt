package com.example.meditation.di

import android.app.Application
import com.example.meditation.model.UserSettingsRepository
import com.example.meditation.service.MusicServiceHelper
import com.example.meditation.util.NotificationHelper
import com.example.meditation.view.dialog.LevelSelectDialog
import com.example.meditation.view.dialog.ThemeSelectedDialog
import com.example.meditation.view.dialog.TimeSelectDialog
import com.example.meditation.view.main.MainFragment
import com.example.meditation.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val appModule = module {
    factory { MainFragment() }
    factory { LevelSelectDialog() }
    factory { ThemeSelectedDialog() }
    factory { TimeSelectDialog() }
    factory { NotificationHelper(get()) }
    factory { MusicServiceHelper(get()) }
    factory { UserSettingsRepository() }

    viewModel { MainViewModel(androidContext() as Application) }
}

