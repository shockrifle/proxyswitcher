package com.danielb.proxyswitcher.model

const val DEFAULT_ID = -1

data class Proxy(var id: Int = DEFAULT_ID, var name: String = "", var host: String = "", var description: String = "", var selected: Boolean = false)