package com.danielb.proxyswitcher

const val DEFAULT_ID = -1

data class Proxy(val id: Int = DEFAULT_ID, val name: String = "", val host: String = "", val description: String = "", var selected: Boolean = false)