package com.danielb.proxyswitcher.repository

import android.content.Context
import android.content.SharedPreferences
import com.danielb.proxyswitcher.model.DEFAULT_PROXY_ID
import com.danielb.proxyswitcher.model.Proxy
import com.danielb.proxyswitcher.util.APP_PREFS_FILE_NAME
import com.danielb.proxyswitcher.util.PREF_SELECTED_PROXY_ID
import com.google.gson.Gson
import java.lang.ref.WeakReference

object ProxyRepository {

    private const val PROXIES_PREFS_FILE_NAME = "proxies"

    var context: WeakReference<Context> = WeakReference<Context>(null)

    fun getProxies(): List<Proxy> {
        val proxies = mutableListOf<Proxy>()
        getProxyPrefs()?.all?.forEach {
            parseProxy(it.value as String)?.let { proxy ->
                proxies.add(proxy)
            }
        }
        return proxies
    }

    fun getProxyById(id: Int) = parseProxy(getProxyPrefs()?.getString(id.toString(), null))

    private fun parseProxy(json: String?): Proxy? = Gson().fromJson(json, Proxy::class.java)

    fun save(proxy: Proxy) {
        if (proxy.id > DEFAULT_PROXY_ID) {
            saveInternal(proxy)
        } else {
            proxy.id = (getProxies().sortedBy { it.id }.lastOrNull()?.id ?: DEFAULT_PROXY_ID) + 1
            saveInternal(proxy)
        }
    }

    private fun saveInternal(proxy: Proxy) {
        getProxyPrefs()?.edit()?.putString(proxy.id.toString(), Gson().toJson(proxy))?.apply()
    }

    fun deleteById(id: Int) {
        getProxyPrefs()?.edit()?.remove(id.toString())?.apply()
    }

    fun saveSelection(id: Int) {
        getAppPrefs()?.edit()?.putInt(PREF_SELECTED_PROXY_ID, id)?.apply()
    }

    fun restoreSelection() = getAppPrefs()?.getInt(PREF_SELECTED_PROXY_ID, DEFAULT_PROXY_ID) ?: DEFAULT_PROXY_ID

    private fun getProxyPrefs(): SharedPreferences? {
        return context.get()?.getSharedPreferences(PROXIES_PREFS_FILE_NAME, Context.MODE_PRIVATE)
    }

    private fun getAppPrefs(): SharedPreferences? {
        return context.get()?.getSharedPreferences(APP_PREFS_FILE_NAME, Context.MODE_PRIVATE)
    }
}