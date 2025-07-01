package com.mayank.wifisync.data.infrastructure

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mayank.wifisync.domain.usecase.GetUsersUseCase
import com.mayank.wifisync.presentation.ui.ApiResult
import com.mayank.wifisync.utils.WiFiMonitor.isWifiConnected
import kotlinx.coroutines.delay
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import androidx.work.ListenableWorker.Result as WorkResult

class  FetchUserWorker(
    private val context: Context,
    workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams), KoinComponent {
    private val retryDelays = listOf(1, 2, 4)
    private val usersUseCase:GetUsersUseCase by inject()

    override suspend fun doWork(): Result {
        if (!isWifiConnected(context)) {
            Log.d("FetchUserWorker", "Wi-Fi not available")
            return Result.failure()
        }
        for ((index, minutes) in retryDelays.withIndex()) {
            when (usersUseCase.refreshUsers()) {
                is ApiResult.Success -> {
                    Log.d(
                        "FetchUserWorker",
                        "Data fetched successfully on attempt ${index + 1}"
                    )
                    return Result.success()
                }

                is ApiResult.Error -> {
                    if (index < retryDelays.lastIndex) {
                        Log.d(
                            "FetchUserWorker",
                            "Fetch failed, retrying in $minutes minute(s)"
                        )
                        delay(minutes * 60_000L)
                        continue
                    } else {
                        Log.e("FetchUserWorker", "All retries exhausted, failing")
                        return Result.failure()
                    }
                }

                else -> Result.failure()
            }
        }
        return Result.failure()
    }
}