package com.danielb.proxyswitcher.repository

import com.danielb.proxyswitcher.model.Proxy

object ProxyRepository {

    private val proxies = sortedMapOf(Pair(0, Proxy(0, "asd")), Pair(1, Proxy(1, "bbb")), Pair(2, Proxy(2, "ccc")))

    fun getProxies() = ArrayList(proxies.values)

    fun getProxyById(id: Int) = proxies[id]

    fun save(proxy: Proxy) {
        if (proxy.id > -1) {
            proxies.put(proxy.id, proxy)
        } else {
            val nextIndex = proxies.keys.last() + 1
            proxy.id = nextIndex
            proxies.put(nextIndex, proxy)
        }
    }

}