package com.danielb.proxyswitcher.server

import fi.iki.elonen.NanoHTTPD
import java.io.IOException

class NanoServer @Throws(IOException::class) constructor() : NanoHTTPD(8080) {

    override fun serve(session: IHTTPSession): NanoHTTPD.Response {
        val msg = "function FindProxyForURL(url, host) { return \"PROXY 10.64.65.133:8888;\" }"
        return NanoHTTPD.newFixedLengthResponse("$msg\n")
    }
}