package com.danielb.proxyswitcher

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.util.Log
import java.io.IOException

class PacHostService : Service() {
    companion object {
        var isOn: Boolean = false
        val TAG = PacHostService::class.java.simpleName
    }

    private var nano: NanoServer? = null

    override fun onCreate() {
        Log.e(TAG, "service started")
        super.onCreate()

        isOn = true

        nano = NanoServer()

        start()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return Service.START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()

        nano?.stop()
        stopAsForeground()
        isOn = false
    }


    private fun start() {
        try {
            nano?.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        runAsForeground()
    }

    fun stopAsForeground() {
        stopForeground(true)
    }

    private fun runAsForeground() {

        val notification = NotificationCompat.Builder(this, "")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentText("localhost:8080")
                .setContentTitle("Server running...")
                .setTicker("Server running...").build()

        startForeground(1, notification)
    }
}