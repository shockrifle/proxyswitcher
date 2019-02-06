package com.danielb.proxyswitcher

import android.content.Context
import java.lang.ref.WeakReference

class Navigator(context: Context) {

    val context: WeakReference<Context> = WeakReference(context)

    fun toProxyDetail() {

    }

}