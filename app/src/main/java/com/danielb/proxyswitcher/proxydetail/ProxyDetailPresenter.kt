package com.danielb.proxyswitcher.proxydetail

import com.danielb.proxyswitcher.Navigator
import com.danielb.proxyswitcher.Proxy

class ProxyDetailPresenter(private val navigator: Navigator) {

    var responseCallback: ResponseCallback? = null

    private val proxies = listOf(Proxy(0, "asd"), Proxy(1, "ccc"), Proxy(2, "vvv"))

    fun getProxy(id: Int) {
        responseCallback?.onResponse(proxies.firstOrNull { it.id == id } ?: Proxy())
    }

    fun save(proxy: Proxy) {

    }

    interface ResponseCallback {
        fun onResponse(response: Proxy)
    }

}
