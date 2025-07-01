package com.mayank.wifisync

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.mayank.wifisync.utils.WiFiMonitor
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class WiFiMonitorTest {
    @Mock
    lateinit var context: Context
    @Mock
    lateinit var connectivityManager: ConnectivityManager
    @Mock
    lateinit var network: Network
    @Mock
    lateinit var networkCapabilities: NetworkCapabilities

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        whenever(context.getSystemService(Context.CONNECTIVITY_SERVICE))
            .thenReturn(connectivityManager)
    }
    @Test
    fun isWifiConnectedWithInternet() {
        whenever(connectivityManager.activeNetwork).thenReturn(network)
        whenever(connectivityManager.getNetworkCapabilities(network))
            .thenReturn(networkCapabilities)
        whenever(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
            .thenReturn(true)
        whenever(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET))
            .thenReturn(true)

        val result = WiFiMonitor.isWifiConnected(context)
        assertTrue(result)
    }
}