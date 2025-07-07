package com.mayank.wifisync.presentation.ui

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.mayank.wifisync.data.infrastructure.ScheduleUserWorker
import com.mayank.wifisync.data.infrastructure.WifiStateReceiver
import com.mayank.wifisync.presentation.theme.WiFiSyncTheme
import com.mayank.wifisync.presentation.ui.screen.AppNavHost

class MainActivity : ComponentActivity() {
    private val wifiReceiver = WifiStateReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ScheduleUserWorker.schedule(applicationContext)
        setContent {
            WiFiSyncTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavHost()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(wifiReceiver, filter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(wifiReceiver)
    }
}