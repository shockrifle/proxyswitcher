package com.danielb.proxyswitcher

import android.databinding.BaseObservable
import android.view.View
import android.widget.CheckBox

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