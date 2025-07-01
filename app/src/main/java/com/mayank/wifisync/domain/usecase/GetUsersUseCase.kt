package com.mayank.wifisync.domain.usecase

import com.mayank.wifisync.data.local.User
import com.mayank.wifisync.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import com.mayank.wifisync.presentation.ui.state.ApiResult

class GetUsersUseCase(private val repository: UserRepository) {
    operator fun invoke(): Flow<ApiResult<List<User>>> = repository.getUsers()
    suspend fun refreshUsers(): ApiResult<List<User>> = repository.refreshUsers()
}