package com.mayank.wifisync.presentation.ui

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T, val message: String) : ApiResult<T>()
    data class Error(val message: String) : ApiResult<Nothing>()
    data object Loading : ApiResult<Nothing>()
}