package com.example.meditation.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder

class MusicServiceHelper(val context: Context) {

    private var musicService: MusicService? = null
    fun bindService() {
        val serviceConnection: ServiceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, ibinder: IBinder?) {
                val binder = ibinder as MusicService.MusicBinder
                musicService = binder.getService()
            }

            override fun onServiceDisconnected(p0: ComponentName?) {

            }
        }
        val intent = Intent(context, MusicService::class.java)
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    fun startBgm() {
        musicService?.startBgm()
    }

    fun stopBgm() {
        musicService?.stopBgm()
    }

    fun setVolume(volume: Int) {
        musicService?.setVolume(volume)
    }

    fun ringFinalGong() {
        musicService?.ringFinalGong()
    }
}