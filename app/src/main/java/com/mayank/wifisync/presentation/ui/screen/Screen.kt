package com.mayank.wifisync.presentation.ui.screen

sealed class Screen(val route: String) {
    object UserList : Screen("user_list")
    object UserDetail : Screen("user_detail")
}
