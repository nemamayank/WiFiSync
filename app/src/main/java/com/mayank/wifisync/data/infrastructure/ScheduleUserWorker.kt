package com.mayank.wifisync.data.infrastructure

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object ScheduleUserWorker {
    fun schedule(context: Context) {
        val oneTimeRequest = OneTimeWorkRequestBuilder<FetchUserWorker>()
            .setInitialDelay(4, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "FetchUserWorker",
            ExistingWorkPolicy.REPLACE,
            oneTimeRequest
        )

//        val periodicRequest = PeriodicWorkRequestBuilder<FetchUserWorker>(
//            1, TimeUnit.HOURS)
//            .setConstraints(constraints)
//            .setBackoffCriteria(
//                BackoffPolicy.EXPONENTIAL,
//                1, TimeUnit.MINUTES
//            )
//            .build()

//        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
//            "FetchUserWorker",
//            ExistingPeriodicWorkPolicy.REPLACE,
//            periodicRequest
//        )
    }
}