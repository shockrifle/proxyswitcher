package com.danielb.proxyswitcher.proxylist

import android.view.View
import android.widget.CheckBox
import androidx.databinding.BaseObservable
import com.danielb.proxyswitcher.model.Proxy

class ProxyListItemViewModel(private val presenter: ProxyListPresenter) : BaseObservable() {

    private var data: Proxy = Proxy()

    val name
        get() = data.name
    val selected
        get() = data.selected


    fun update(proxy: Proxy) {
        data = proxy
        notifyChange()
    }

    fun onClick() {
        presenter.toProxyDetail(data.id)
    }

    fun onSelected(view: View) {
        presenter.onProxyChecked(data.id, (view as? CheckBox)?.isChecked ?: false)
    }

}