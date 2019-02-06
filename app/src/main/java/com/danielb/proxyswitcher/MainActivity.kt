package com.danielb.proxyswitcher

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import com.danielb.proxyswitcher.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), ResponseCallback {

    private lateinit var binding: ActivityMainBinding
    private val navigator = Navigator(this)
    private val presenter = ProxyListPresenter(navigator)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = getString(R.string.title_proxies)

        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        binding.proxyList.layoutManager = LinearLayoutManager(this)
        binding.proxyList.adapter = ProxyListAdapter(presenter)
        presenter.responseCallback = this
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        presenter.getProxies()
    }

    override fun onResponse(response: List<Proxy>) {
        (binding.proxyList.adapter as? ProxyListAdapter)?.data = response
    }


    //Start service:
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        startForegroundService(Intent(this, PacHostService::class.java))
//    } else {
//        startService(Intent(this, PacHostService::class.java))
//    }
}
