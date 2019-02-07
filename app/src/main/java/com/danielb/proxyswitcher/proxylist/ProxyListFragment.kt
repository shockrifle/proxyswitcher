package com.danielb.proxyswitcher.proxylist

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.danielb.proxyswitcher.MainActivity
import com.danielb.proxyswitcher.Navigator
import com.danielb.proxyswitcher.R
import com.danielb.proxyswitcher.databinding.FragmentProxyListBinding
import com.danielb.proxyswitcher.model.Proxy

class ProxyListFragment : Fragment(), ProxyListPresenter.ResponseCallback {

    private lateinit var binding: FragmentProxyListBinding
    private lateinit var navigator: Navigator
    private lateinit var presenter: ProxyListPresenter

    companion object {
        fun newInstance() = ProxyListFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is MainActivity) {
            navigator = Navigator(context)
            presenter = ProxyListPresenter(navigator)
        } else {
            throw IllegalArgumentException("Only works in MainActivity")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProxyListBinding.inflate(inflater, container, false)

        binding.proxyList.layoutManager = LinearLayoutManager(activity)
        binding.proxyList.adapter = ProxyListAdapter(presenter)

        return binding.root
    }


    override fun onStart() {
        super.onStart()
        presenter.responseCallback = this
        presenter.getProxies()
    }

    override fun onStop() {
        presenter.responseCallback = null
        super.onStop()
    }

    override fun onResponse(response: List<Proxy>) {
        (binding.proxyList.adapter as? ProxyListAdapter)?.data = response
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.proxy_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?) =
            when (item?.itemId) {
                R.id.add_proxy -> {
                    navigator.toProxyDetail()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

}