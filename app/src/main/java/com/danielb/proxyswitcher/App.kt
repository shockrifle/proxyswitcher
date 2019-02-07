package com.danielb.proxyswitcher

import android.app.Application
import com.danielb.proxyswitcher.repository.ProxyRepository
import java.lang.ref.WeakReference

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ProxyRepository.context = WeakReference(this)
    }
}
