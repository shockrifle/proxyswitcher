package com.danielb.proxyswitcher.server

import android.text.TextUtils
import android.util.Log
import com.danielb.proxyswitcher.repository.ProxyRepository
import fi.iki.elonen.NanoHTTPD
import java.io.IOException

class NanoServer @Throws(IOException::class) constructor() : NanoHTTPD(8080) {


    override fun serve(session: IHTTPSession): NanoHTTPD.Response {
        val proxy = ProxyRepository.getProxyById(ProxyRepository.restoreSelection())
        var host = "DIRECT"
        if (!TextUtils.isEmpty(proxy?.host)) {
            host = "PROXY ${proxy!!.host}"
        }
        Log.i(this.javaClass.simpleName, "Host served: $host")
        val msg = "function FindProxyForURL(url, host) { return \"$host;\" }"
        return NanoHTTPD.newFixedLengthResponse("$msg\n")
    }
}