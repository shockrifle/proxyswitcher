package com.danielb.proxyswitcher.proxydetail

import com.danielb.proxyswitcher.Navigator
import com.danielb.proxyswitcher.model.Proxy
import com.danielb.proxyswitcher.repository.ProxyRepository

class ProxyDetailPresenter(private val navigator: Navigator) {

    var responseCallback: ResponseCallback? = null

    fun getProxy(id: Int) {
        responseCallback?.onResponse(ProxyRepository.getProxyById(id) ?: Proxy())
    }

    fun save(proxy: Proxy) {
        ProxyRepository.save(proxy)
    }

    interface ResponseCallback {
        fun onResponse(response: Proxy)
    }

}
