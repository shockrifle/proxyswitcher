package com.danielb.proxyswitcher

import android.app.Application
import com.danielb.proxyswitcher.repository.ProxyRepository
import com.danielb.proxyswitcher.util.SystemServiceLocator
import java.lang.ref.WeakReference

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        SystemServiceLocator.context = WeakReference(this)
        ProxyRepository.context = WeakReference(this)
    }
}
