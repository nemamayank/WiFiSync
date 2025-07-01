package com.mayank.wifisync.data.repository

import android.content.Context
import android.util.Log
import com.mayank.wifisync.R
import com.mayank.wifisync.data.local.User
import com.mayank.wifisync.data.local.UserDao
import com.mayank.wifisync.data.remote.ApiService
import com.mayank.wifisync.data.remote.toUser
import com.mayank.wifisync.domain.repository.UserRepository
import com.mayank.wifisync.presentation.ui.ApiResult
import com.mayank.wifisync.utils.TimeUtils.isDataStaled
import com.mayank.wifisync.utils.TimeUtils.now
import com.mayank.wifisync.utils.WiFiMonitor.isWifiConnected
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val api: ApiService,
    private val context: Context
) : UserRepository {
    lateinit var fetchedUser: User
    companion object { const val DATA_EXPIRY_TIME = 60 * 60 * 1000L } // 60 min
    override fun getUsers(): Flow<ApiResult<List<User>>> = flow {
        emit(ApiResult.Loading)
        try {
            userDao.getAll().collect { localUsers ->
                val shouldRefresh = localUsers.isEmpty() || isDataStaled(localUsers, DATA_EXPIRY_TIME)
                if (shouldRefresh && isWifiConnected(context)) {
                    Log.d("Repository", "ShouldRefresh: Calling Api...")
                    refreshUsers()
                    return@collect
                }
                if (localUsers.isNotEmpty()) {
                    val minutesAgo = (now - localUsers.first().fetchedAt) / 60_000
                    val message = context.getString(
                        if (minutesAgo < 1) R.string.fetched_new_data
                        else R.string.showing_old_data
                    )
                    fetchedUser = localUsers.first()
                    emit(ApiResult.Success(localUsers, message))
                } else {
                    emit(ApiResult.Error(context.getString(R.string.no_data_available)))
                }
            }
        } catch (e: Exception) {
            emit(ApiResult.Error(context.getString(R.string.no_data_available)))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun refreshUsers(): ApiResult<List<User>> = withContext(Dispatchers.IO) {
        try {
            val apiList = api.getUsers().data
            if (apiList.isEmpty()) {
                userDao.updateAll(emptyList())
                return@withContext ApiResult.Error(context.getString(R.string.no_data_available))
            }
            val pickCount = (1..minOf(6, apiList.size)).random()
            val dbUsers: List<User> = apiList
                .shuffled()
                .take(pickCount)
                .mapNotNull { it.toUser(fetchedAt = now) }

            userDao.updateAll(dbUsers)
            Log.d("Repository","API Called: Original: ${apiList.size}, Inserted: ${dbUsers.size}")
            ApiResult.Success(dbUsers, context.getString(R.string.fetched_new_data))
        } catch (e: Exception) {
            Log.d("Repository", "API Exception: ${e.message}")
            ApiResult.Error(context.getString(R.string.no_data_available))
        }
    }
}