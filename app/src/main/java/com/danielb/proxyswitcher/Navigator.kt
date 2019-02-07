package com.danielb.proxyswitcher

import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.StringRes
import com.danielb.proxyswitcher.model.DEFAULT_PROXY_ID
import com.danielb.proxyswitcher.proxydetail.ProxyDetailFragment
import com.danielb.proxyswitcher.proxylist.ProxyListFragment
import com.danielb.proxyswitcher.server.PacHostService
import java.lang.ref.WeakReference

class Navigator(activity: MainActivity?) {

    private val activity: WeakReference<MainActivity?> = WeakReference(activity)

    val topFragment: androidx.fragment.app.Fragment?
        get() = activity.get()?.supportFragmentManager?.findFragmentById(R.id.container)

    fun toProxyDetail(id: Int = DEFAULT_PROXY_ID) {
        replaceFragment(ProxyDetailFragment.newInstance(id))
    }

    fun toProxyList() {
        replaceFragment(ProxyListFragment.newInstance())
    }

    private fun replaceFragment(fragment: androidx.fragment.app.Fragment) {
        activity.get()?.run {
            val transaction = supportFragmentManager.beginTransaction()
            if (supportFragmentManager.fragments.size > 0) {
                transaction.addToBackStack(null)
            }
            transaction
                    .replace(R.id.container, fragment)
                    .commit()
        }
    }

    fun displayToast(@StringRes msg: Int) {
        if (activity.get() != null) {
            Toast.makeText(activity.get(), msg, Toast.LENGTH_SHORT).show()
        }
    }

    fun back() {
        activity.get()?.onBackPressed()
    }

    fun startServer() {
        activity.get()?.run {
            val intent = Intent(this, PacHostService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent)
            } else {
                startService(intent)
            }
        }
    }

    fun stopServer() {
        activity.get()?.run {
            PendingIntent.getService(this, 0, Intent(this, PacHostService::class.java).putExtra(PacHostService.EXTRA_STOP_SERVER, true), 0)
        }
    }

}

interface BackListener {

    /**
     * @return true if the button press is consumed
     */
    fun onBackPressed(): Boolean
}