package com.mayank.wifisync.utils

import com.mayank.wifisync.data.local.User

object TimeUtils {
    val now = System.currentTimeMillis()
    fun isDataStaled(users: List<User>, dataExpiryTime: Long): Boolean {
        val firstFetchedAt = users.firstOrNull()?.fetchedAt ?: return true
        val dataAge = now - firstFetchedAt
        return dataAge >= dataExpiryTime
    }
}