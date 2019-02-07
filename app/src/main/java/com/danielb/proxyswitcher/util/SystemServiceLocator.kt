package com.danielb.proxyswitcher.util

import android.content.Context
import android.net.wifi.WifiManager
import java.lang.ref.WeakReference


object SystemServiceLocator {

    var context: WeakReference<Context> = WeakReference<Context>(null)

    fun getWifiService() = context.get()?.applicationContext?.getSystemService(Context.WIFI_SERVICE) as WifiManager?

}
