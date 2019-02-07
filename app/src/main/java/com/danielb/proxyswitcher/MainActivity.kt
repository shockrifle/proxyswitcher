package com.danielb.proxyswitcher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
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
        PacHostService.isOn.observe(this, Observer {
            menu?.findItem(R.id.start_stop_server)?.icon = getDrawable(
                    if (it)
                        R.drawable.ic_stop_white_24dp
                    else
                        R.drawable.ic_play_arrow_white_24dp
            )
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) =
            when (item?.itemId) {
                R.id.start_stop_server -> {
                    if (PacHostService.isOn.value == false) {
                        navigator.startServer()
                    } else {
                        navigator.stopServer()
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
