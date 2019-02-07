package com.danielb.proxyswitcher.proxylist

import com.danielb.proxyswitcher.Navigator
import com.danielb.proxyswitcher.model.DEFAULT_PROXY_ID
import com.danielb.proxyswitcher.model.Proxy
import com.danielb.proxyswitcher.repository.ProxyRepository
import com.danielb.proxyswitcher.util.SystemServiceLocator


class ProxyListPresenter(private val navigator: Navigator) {

    private var proxies: List<Proxy> = listOf()

    var responseCallback: ResponseCallback? = null

    fun getProxies() {
        proxies = ProxyRepository.getProxies()
        val selectedId = ProxyRepository.restoreSelection()
        proxies.forEach { it.selected = it.id == selectedId }
        responseCallback?.onResponse(proxies)
    }

    fun toProxyDetail(id: Int = DEFAULT_PROXY_ID) = navigator.toProxyDetail(id)

    fun onProxyChecked(id: Int, checked: Boolean) {
        ProxyRepository.saveSelection(if (checked) id else DEFAULT_PROXY_ID)
        restartWifi()
        getProxies()
    }

    private fun restartWifi() {
        val wifiManager = SystemServiceLocator.getWifiService()
        wifiManager?.isWifiEnabled = false
        wifiManager?.isWifiEnabled = true
    }

    interface ResponseCallback {
        fun onResponse(response: List<Proxy>)
    }

}
