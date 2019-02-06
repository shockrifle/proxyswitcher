package com.danielb.proxyswitcher

class ProxyListPresenter(private val navigator: Navigator) {

    var responseCallback: ResponseCallback? = null

    private val proxies = listOf(Proxy(0, "asd"), Proxy(1, "ccc"), Proxy(2, "vvv"))

    fun getProxies() {
        responseCallback?.onResponse(proxies)
    }

    fun toProxyDetail(id: Int = -1) = navigator.toProxyDetail(id)

    fun onProxyChecked(id: Int, checked: Boolean) {
        proxies.forEach { it.selected = it.id == id && checked }
        getProxies()
    }

}

interface ResponseCallback {
    fun onResponse(response: List<Proxy>)
}