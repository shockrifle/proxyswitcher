package com.danielb.proxyswitcher

import android.annotation.TargetApi
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import java.io.IOException


class PacHostService : Service() {
    companion object {
        var isOn: Boolean = false
        val TAG = PacHostService::class.java.simpleName
        const val CHANNEL_ID_FOREGROUND = "com.danielb.proxyswitcher.notification.CHANNEL_ID_FOREGROUND"
    }

    private var nano: NanoServer? = null
    private var lastShownNotificationId = 0

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

        createAndShowForegroundNotification(this, 1)
    }

    fun stopAsForeground() {
        stopForeground(true)
    }

    private fun createAndShowForegroundNotification(service: Service, notificationId: Int) {

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)

        val builder = getNotificationBuilder(service, CHANNEL_ID_FOREGROUND, NotificationManagerCompat.IMPORTANCE_LOW)
        builder.setOngoing(true)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentText("localhost:8080")
                .setContentTitle("Server running...")
                .setTicker("Server running...")

        val notification = builder.build()

        service.startForeground(notificationId, notification)

        if (notificationId != lastShownNotificationId) {
            val nm = service.getSystemService(Activity.NOTIFICATION_SERVICE) as NotificationManager
            nm.cancel(lastShownNotificationId)
        }
        lastShownNotificationId = notificationId
    }

    private fun getNotificationBuilder(context: Context, channelId: String, importance: Int): NotificationCompat.Builder {
        val builder: NotificationCompat.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            prepareChannel(context, channelId, importance)
        }
        builder = NotificationCompat.Builder(context, channelId)
        return builder
    }

    @TargetApi(26)
    private fun prepareChannel(context: Context, id: String, importance: Int) {
        val appName = context.getString(R.string.app_name)
        val description = context.getString(R.string.notifications_channel_description)
        val nm = context.getSystemService(Activity.NOTIFICATION_SERVICE) as? NotificationManager

        if (nm != null) {
            var nChannel: NotificationChannel? = nm.getNotificationChannel(id)

            if (nChannel == null) {
                nChannel = NotificationChannel(id, appName, importance)
                nChannel.description = description
                nm.createNotificationChannel(nChannel)
            }
        }
    }
}