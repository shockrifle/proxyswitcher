package com.danielb.proxyswitcher

import android.support.v4.app.Fragment
import java.lang.ref.WeakReference

class Navigator(activity: MainActivity?) {

    val activity: WeakReference<MainActivity?> = WeakReference(activity)

    fun toProxyDetail(id: Int = -1) {

    }

    fun toProxyList() {
        replaceFragment(ProxyListFragment.newInstance())
    }

    private fun replaceFragment(fragment: Fragment) {
        activity.get()?.run {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, fragment)
                    .commit()
        }
    }

}