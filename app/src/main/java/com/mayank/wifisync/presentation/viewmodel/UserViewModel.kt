package com.mayank.wifisync.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayank.wifisync.data.local.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.mayank.wifisync.presentation.ui.ApiResult
import com.mayank.wifisync.domain.usecase.GetUsersUseCase

class UserViewModel(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {
    private val _usersState = MutableStateFlow<ApiResult<List<User>>>(ApiResult.Loading)
    val usersState: StateFlow<ApiResult<List<User>>> = _usersState.asStateFlow()
    private val _selectedUser = MutableStateFlow<User?>(null)
    val selectedUser: StateFlow<User?> = _selectedUser

    init {
        println("DEBUG: UserViewModel instance created: $this")
    }

    fun fetchUsers() {
        viewModelScope.launch {
            getUsersUseCase().collect { result ->
                _usersState.value = result
            }
        }
    }
    fun selectUser(user: User) {
        _selectedUser.value = user
    }
}