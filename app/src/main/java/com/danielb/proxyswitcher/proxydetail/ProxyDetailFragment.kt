package com.danielb.proxyswitcher.proxydetail

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.danielb.proxyswitcher.BackListener
import com.danielb.proxyswitcher.MainActivity
import com.danielb.proxyswitcher.Navigator
import com.danielb.proxyswitcher.R
import com.danielb.proxyswitcher.databinding.FragmentProxyDetailBinding
import com.danielb.proxyswitcher.model.DEFAULT_ID
import com.danielb.proxyswitcher.model.Proxy

class ProxyDetailFragment : Fragment(), ProxyDetailPresenter.ResponseCallback, BackListener {

    private lateinit var binding: FragmentProxyDetailBinding
    private lateinit var navigator: Navigator
    private lateinit var presenter: ProxyDetailPresenter

    companion object {

        private const val EXTRA_PROXY_ID = "EXTRA_PROXY_ID"

        fun newInstance(id: Int): ProxyDetailFragment {
            val fragment = ProxyDetailFragment()

            val bundle = Bundle()
            bundle.putInt(EXTRA_PROXY_ID, id)
            fragment.arguments = bundle

            return fragment
        }

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is MainActivity) {
            navigator = Navigator(context)
            presenter = ProxyDetailPresenter(navigator)
        } else {
            throw IllegalArgumentException("Only works in MainActivity")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProxyDetailBinding.inflate(inflater, container, false)
        binding.viewModel = ProxyDetailViewModel(presenter)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        presenter.responseCallback = this
        presenter.getProxy(getProxyId())
    }

    override fun onStop() {
        presenter.responseCallback = null
        super.onStop()
    }

    override fun onResponse(response: Proxy) {
        binding.viewModel?.update(response)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.proxy_detail_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?) =
            when (item?.itemId) {
                R.id.save_proxy -> {
                    binding.viewModel?.save()
                    true
                }
                R.id.delete_proxy -> {
                    binding.viewModel?.delete()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    override fun onBackPressed(): Boolean {
        //TODO: show discard dialog
        return false
    }

    fun getProxyId() = arguments?.getInt(EXTRA_PROXY_ID, DEFAULT_ID) ?: DEFAULT_ID

}