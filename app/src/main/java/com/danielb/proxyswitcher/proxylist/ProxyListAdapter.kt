package com.danielb.proxyswitcher.proxylist

import android.view.LayoutInflater
import android.view.ViewGroup
import com.danielb.proxyswitcher.databinding.ListItemProxyBinding
import com.danielb.proxyswitcher.model.Proxy

class ProxyListAdapter(private val presenter: ProxyListPresenter, data: List<Proxy> = emptyList()) : androidx.recyclerview.widget.RecyclerView.Adapter<ProxyListItemViewHolder>() {

    var data = data
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ProxyListItemViewHolder {
        val binding = ListItemProxyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        binding.viewModel = ProxyListItemViewModel(presenter)
        return ProxyListItemViewHolder(binding)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ProxyListItemViewHolder, i: Int) = holder.bind(data[i])

}

class ProxyListItemViewHolder(private val binding: ListItemProxyBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
    fun bind(proxy: Proxy) {
        binding.viewModel?.update(proxy)
    }
}

