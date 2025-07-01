package com.mayank.wifisync.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mayank.wifisync.presentation.viewmodel.UserViewModel
import org.koin.androidx.compose.koinViewModel

@Preview(showBackground = true)
@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val userViewModel: UserViewModel = koinViewModel()
    NavHost(navController, startDestination = Screen.UserList.route) {
        composable("user_list") {
            UserListScreen(
                userViewModel = userViewModel,
                onUserClick = {
                    userViewModel.selectUser(it)
                    navController.navigate("user_detail")
                }
            )
        }
        composable("user_detail") {
            UserDetailScreen(userViewModel = userViewModel, navController = navController)
        }
    }
}