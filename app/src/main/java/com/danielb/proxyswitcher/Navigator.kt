package com.danielb.proxyswitcher

import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.widget.Toast
import com.danielb.proxyswitcher.model.DEFAULT_PROXY_ID
import com.danielb.proxyswitcher.proxydetail.ProxyDetailFragment
import com.danielb.proxyswitcher.proxylist.ProxyListFragment
import java.lang.ref.WeakReference

class Navigator(activity: MainActivity?) {

    private val activity: WeakReference<MainActivity?> = WeakReference(activity)

    val topFragment: Fragment?
        get() = activity.get()?.supportFragmentManager?.findFragmentById(R.id.container)

    fun toProxyDetail(id: Int = DEFAULT_PROXY_ID) {
        replaceFragment(ProxyDetailFragment.newInstance(id))
    }

    fun toProxyList() {
        replaceFragment(ProxyListFragment.newInstance())
    }

    private fun replaceFragment(fragment: Fragment) {
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

}

interface BackListener {

    /**
     * @return true if the button press is consumed
     */
    fun onBackPressed(): Boolean
}