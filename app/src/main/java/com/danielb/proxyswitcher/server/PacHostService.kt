package com.danielb.proxyswitcher.server

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import com.danielb.proxyswitcher.MainActivity
import com.danielb.proxyswitcher.R
import java.io.IOException


class PacHostService : Service() {
    companion object {
        var isOn: Boolean = false
        val TAG: String = PacHostService::class.java.simpleName
        const val CHANNEL_ID_FOREGROUND = "com.danielb.proxyswitcher.notification.CHANNEL_ID_FOREGROUND"
        const val EXTRA_STOP_SERVER = "com.danielb.proxyswitcher.notification.STOP_SERVER"
    }

    private var nano: NanoServer? = null
    private var lastShownNotificationId = 0

    override fun onCreate() {
        super.onCreate()

        isOn = true

        nano = NanoServer()

        start()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.getBooleanExtra(EXTRA_STOP_SERVER, false) == true) {
            stopSelf()
        }
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
            createAndShowForegroundNotification(this, 1)
        } catch (e: IOException) {
            Log.e(TAG, "Cannot start server", e)
            stopSelf()
        }
    }

    private fun stopAsForeground() {
        stopForeground(true)
    }

    private fun createAndShowForegroundNotification(service: Service, notificationId: Int) {

        val notification = getNotificationBuilder(service, CHANNEL_ID_FOREGROUND, NotificationManagerCompat.IMPORTANCE_LOW)
                .setOngoing(true)
                .setContentIntent(PendingIntent.getActivity(this, 0, Intent(this, MainActivity::class.java), 0))
                .setSmallIcon(R.drawable.ic_notification)
                .setContentText("localhost:8080")
                .setContentTitle("Server running...")
                .setTicker("Server running...")
                .addAction(R.drawable.ic_stop_black_24dp, "Stop server",
                        PendingIntent.getService(this, 0, Intent(this, PacHostService::class.java).putExtra(EXTRA_STOP_SERVER, true), 0))
                .build()

        service.startForeground(notificationId, notification)

        if (notificationId != lastShownNotificationId) {
            val nm = service.getSystemService(Activity.NOTIFICATION_SERVICE) as NotificationManager
            nm.cancel(lastShownNotificationId)
        }
        lastShownNotificationId = notificationId
    }

    private fun getNotificationBuilder(context: Context, channelId: String, importance: Int): NotificationCompat.Builder {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            prepareChannel(context, channelId, importance)
        }
        return NotificationCompat.Builder(context, channelId)
    }

    @RequiresApi(Build.VERSION_CODES.O)
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