package com.danielb.proxyswitcher

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import com.danielb.proxyswitcher.databinding.ActivityMainBinding
import com.danielb.proxyswitcher.server.PacHostService


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
        if (PacHostService.isOn) {
            menu?.findItem(R.id.start_stop_server)?.icon = getDrawable(R.drawable.ic_stop_white_24dp)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) =
            when (item?.itemId) {
                R.id.start_stop_server -> {
                    if (!PacHostService.isOn) {
                        navigator.startServer()
                        item.icon = getDrawable(R.drawable.ic_stop_white_24dp)
                    } else {
                        navigator.stopServer()
                        item.icon = getDrawable(R.drawable.ic_play_arrow_white_24dp)
                    }
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    override fun onBackPressed() {
        if ((navigator.topFragment as? BackListener)?.onBackPressed() != true) {
            super.onBackPressed()
        }
    }

}
