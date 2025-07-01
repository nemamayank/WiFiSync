package com.mayank.wifisync.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object WiFiMonitor {
    /**
     * Returns true if the device is connected to Wi-Fi and also has internet access.
     */
    fun isWifiConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
                ?: return false
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        val isWifi = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        val hasInternet = capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        return isWifi && hasInternet

    }
}