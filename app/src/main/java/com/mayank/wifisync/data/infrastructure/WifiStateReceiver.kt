package com.mayank.wifisync.data.infrastructure

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.util.Log
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager

class WifiStateReceiver : BroadcastReceiver() {
    private var lastTransport: Int? = null
    override fun onReceive(context: Context, intent: Intent) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val currentCapabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        lastTransport = when {
            currentCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true -> NetworkCapabilities.TRANSPORT_WIFI
            currentCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true -> NetworkCapabilities.TRANSPORT_CELLULAR
            else -> null
        }
        connectivityManager.registerDefaultNetworkCallback(object :
            ConnectivityManager.NetworkCallback() {

            override fun onCapabilitiesChanged(
                network: Network,
                capabilities: NetworkCapabilities
            ) {
                val isWifi = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                val isCellular = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                val isInternetAvailable =
                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                val currentTransport = when {
                    isWifi -> NetworkCapabilities.TRANSPORT_WIFI
                    isCellular -> NetworkCapabilities.TRANSPORT_CELLULAR
                    else -> null
                }

                if (currentTransport == NetworkCapabilities.TRANSPORT_WIFI &&
                    lastTransport != NetworkCapabilities.TRANSPORT_WIFI &&
                    isInternetAvailable
                ) {
                    Log.d("<<<WifiStateReceiver>>>", "<<< Switched to Wi-Fi >>>")
                    val request = OneTimeWorkRequestBuilder<FetchUserWorker>()
                        .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                        .build()
                    WorkManager.getInstance(context).enqueueUniqueWork(
                        "WifiStateReceiver",
                        ExistingWorkPolicy.REPLACE,
                        request
                    )
                }
                lastTransport = currentTransport
            }
        })
    }
}
