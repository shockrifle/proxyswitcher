package com.danielb.proxyswitcher.proxydetail

import android.databinding.BaseObservable
import com.danielb.proxyswitcher.Proxy
import com.danielb.proxyswitcher.util.VisibilityDelegate

class ProxyDetailViewModel(private val presenter: ProxyDetailPresenter) : BaseObservable() {

    private var data: Proxy = Proxy()

    val id
        get() = data.id.toString()
    val name
        get() = data.name
    val host
        get() = data.host
    val description
        get() = data.description
    val idVisibility by VisibilityDelegate { data.id > -1 }

    fun update(proxy: Proxy) {
        data = proxy
        notifyChange()
    }
}