package com.danielb.proxyswitcher.proxydetail

import android.databinding.BaseObservable
import com.danielb.proxyswitcher.model.Proxy
import com.danielb.proxyswitcher.util.VisibilityDelegate

class ProxyDetailViewModel(private val presenter: ProxyDetailPresenter) : BaseObservable() {

    private var data: Proxy = Proxy()

    val id
        get() = data.id.toString()
    var name
        set(value) {
            data.name = value
        }
        get() = data.name
    var host
        set(value) {
            data.host = value
        }
        get() = data.host
    var description
        set(value) {
            data.description = value
        }
        get() = data.description
    val idVisibility by VisibilityDelegate { data.id > -1 }

    fun update(proxy: Proxy) {
        data = proxy
        notifyChange()
    }

    fun save() {
        presenter.save(data)
    }

    fun delete() {
        presenter.delete(data.id)
    }
}