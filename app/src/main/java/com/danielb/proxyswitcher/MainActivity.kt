package com.danielb.proxyswitcher

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.Menu
import com.danielb.proxyswitcher.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val navigator = Navigator(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = getString(R.string.title_proxies)

        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))

        navigator.toProxyList()

        setContentView(binding.root)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onBackPressed() {
        if ((navigator.topFragment as? BackListener)?.onBackPressed() != true) {
            super.onBackPressed()
        }
    }

    //Start service:
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        startForegroundService(Intent(this, PacHostService::class.java))
//    } else {
//        startService(Intent(this, PacHostService::class.java))
//    }
}
