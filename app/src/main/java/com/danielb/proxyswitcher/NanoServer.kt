package com.danielb.proxyswitcher

import fi.iki.elonen.NanoHTTPD
import java.io.IOException

class NanoServer @Throws(IOException::class) constructor() : NanoHTTPD(8080) {

    override fun serve(session: IHTTPSession): NanoHTTPD.Response {
        var msg = "<html><body><h1>Hello server</h1>\n"
        val params = session.parameters
        if (params["username"] == null) {
            msg += "<form action='?' method='get'>\n  <p>Your name: <input type='text' name='username'></p>\n" + "</form>\n"
        } else {
            msg += "<p>Hello, " + params["username"] + "!</p>"
        }
        return NanoHTTPD.newFixedLengthResponse("$msg</body></html>\n")
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            try {
                NanoServer()
            } catch (ioe: IOException) {
                System.err.println("Couldn't start server:\n$ioe")
            }

        }
    }
}