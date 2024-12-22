package com.example.absenkuy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.absenkuy.data.local.UserPreferences
import com.example.absenkuy.data.retrofit.ApiConfig
import com.example.absenkuy.ui.absen.AbsenScreen
import com.example.absenkuy.ui.home.HomeScreen
import com.example.absenkuy.ui.home.HomeViewModel
import com.example.absenkuy.ui.izin.IzinScreen
import com.example.absenkuy.ui.login.LoginScreen
import com.example.absenkuy.ui.mk.MKScreen
import kotlinx.coroutines.flow.Flow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userPreferences = UserPreferences.getInstance(applicationContext)
        setContent {
            AbsenKuyApp(userPreferences.isUserLoggedIn())
        }
    }
}

@Composable
fun AbsenKuyApp(isUserLoggedInFlow: Flow<Boolean>) {
    val navController = rememberNavController()
    val isUserLoggedIn = isUserLoggedInFlow.collectAsState(initial = false)
    val NIM = UserPreferences.getInstance(LocalContext.current).getUserNIM().collectAsState(initial = "")

    NavHost(
        navController = navController,
        startDestination = if (isUserLoggedIn.value) "home/${NIM}" else "login"
    ) {
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable(
            route = "home/{NIM}",
            arguments = listOf(
                navArgument("NIM") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val NIM = backStackEntry.arguments?.getString("NIM")
            val apiService = ApiConfig.apiService
            val userPreferences = UserPreferences.getInstance(LocalContext.current)
            HomeScreen(
                navController = navController,
                homeViewModel = HomeViewModel(userPreferences, apiService)
            )
        }
        composable(
            route = "matkul/{NIM}",
            arguments = listOf(navArgument("NIM") { type = NavType.StringType })
        ) { backStackEntry ->
            val nim = backStackEntry.arguments?.getString("NIM")
            MKScreen(navController = navController, NIM = nim.toString())
        }
        composable("izin") {
            IzinScreen(navController = navController)
        }
        composable("presensi") {
            AbsenScreen(navController = navController)
        }
    }
}








