package com.theapache64.urlsherlock.utils

import java.io.IOException
import java.net.HttpURLConnection
import java.net.HttpURLConnection.HTTP_MOVED_PERM
import java.net.HttpURLConnection.HTTP_MOVED_TEMP
import java.net.URL


object UrlRevealer {

    @Throws(IOException::class)
    fun reveal(url: String): String {

        val con = URL(url).openConnection() as HttpURLConnection
        con.instanceFollowRedirects = false
        con.connect()
        con.inputStream

        if (con.responseCode == HTTP_MOVED_PERM || con.responseCode == HTTP_MOVED_TEMP) {
            val redirectUrl = con.getHeaderField("Location")
            return reveal(redirectUrl)
        }

        return url
    }
}