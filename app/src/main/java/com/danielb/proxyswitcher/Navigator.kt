package com.danielb.proxyswitcher

import android.support.v4.app.Fragment
import com.danielb.proxyswitcher.model.DEFAULT_ID
import com.danielb.proxyswitcher.proxydetail.ProxyDetailFragment
import com.danielb.proxyswitcher.proxylist.ProxyListFragment
import java.lang.ref.WeakReference

class Navigator(activity: MainActivity?) {

    val activity: WeakReference<MainActivity?> = WeakReference(activity)

    val topFragment: Fragment?
        get() = activity.get()?.supportFragmentManager?.findFragmentById(R.id.container)

    fun toProxyDetail(id: Int = DEFAULT_ID) {
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

}

interface BackListener {

    /**
     * @return true if the button press is consumed
     */
    fun onBackPressed(): Boolean
}