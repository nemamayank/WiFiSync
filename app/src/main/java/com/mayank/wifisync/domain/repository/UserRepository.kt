package com.mayank.wifisync.domain.repository

import com.mayank.wifisync.data.local.User
import kotlinx.coroutines.flow.Flow
import com.mayank.wifisync.presentation.ui.ApiResult

interface UserRepository {
    fun getUsers(): Flow<ApiResult<List<User>>>
    suspend fun refreshUsers(): ApiResult<List<User>>
}