package com.danielb.proxyswitcher.proxylist

import com.danielb.proxyswitcher.Navigator
import com.danielb.proxyswitcher.model.DEFAULT_ID
import com.danielb.proxyswitcher.model.Proxy
import com.danielb.proxyswitcher.repository.ProxyRepository

class ProxyListPresenter(private val navigator: Navigator) {

    private var proxies: List<Proxy> = listOf()

    var responseCallback: ResponseCallback? = null

    fun getProxies() {
        proxies = ProxyRepository.getProxies()
        responseCallback?.onResponse(proxies)
    }

    fun toProxyDetail(id: Int = DEFAULT_ID) = navigator.toProxyDetail(id)

    fun onProxyChecked(id: Int, checked: Boolean) {
        proxies.forEach { it.selected = it.id == id && checked }
        getProxies()
    }

    interface ResponseCallback {
        fun onResponse(response: List<Proxy>)
    }

}
