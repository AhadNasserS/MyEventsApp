package com.example.myeventsapp.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.eventmanagment.presntation.screens.auth.SignUpScreen
import com.example.myeventsapp.Screen.auth.AuthViewModel
import com.example.myeventsapp.Screen.auth.LoginScreen
import com.example.myeventsapp.Screen.auth.SplashScreen


@Composable
fun EventsAppNavigation(
    authViewModel: AuthViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
 NavHost(
     navController = navController,
     startDestination = authViewModel.isSignIn.value,
     ) {
     authNavigation(navController,authViewModel)
     mainAppNavigation(navController){
         authViewModel.logout(context)
     }
  }
}

fun NavGraphBuilder.authNavigation(
    navController: NavHostController,
    authViewModel: AuthViewModel
){
    navigation(
        startDestination = Screens.Authentication.Splash.route,
        route = Screens.Authentication.route,
    ){
        composable(Screens.Authentication.Splash.route){
            SplashScreen(navController)
        }


        composable(Screens.Authentication.SignUp.route) {
            SignUpScreen(navController = navController, viewModel = authViewModel)
        }
        composable(Screens.Authentication.Login.route) {
            LoginScreen(navController = navController, viewModel = authViewModel)
        }
    }
}

fun NavGraphBuilder.mainAppNavigation(
    navController: NavHostController,
    logout: ()-> Unit
) {
    navigation(
        startDestination = Screens.MainApp.Home.route,
        route = Screens.MainApp.route,
    ) {

        composable(Screens.MainApp.Home.route) {
            Column(modifier = Modifier.fillMaxSize())
            {

            }
        }
        composable(Screens.MainApp.TaskByDate.route) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Yellow)
            ) {

            }
        }
        composable(Screens.MainApp.CategoryScreen.route) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Red)
            )
            {
                Button(onClick = {
                    logout.invoke()
                }) {
                    Text(text = "LogOut")
                }
            }
        }
        composable(Screens.MainApp.AddScreen.route) {
            Column(modifier = Modifier.fillMaxSize().background(Color.Magenta))
            {

            }
        }
        composable(Screens.MainApp.StaticsScreen.route) {
            Column(modifier = Modifier.fillMaxSize().background(Color.Green))
            {

            }
        }
    }
}


fun NavOptionsBuilder.popUpToTop(navController: NavHostController){
    popUpTo(navController.currentBackStackEntry?.destination?.route?: return){
        saveState = true
        inclusive = true

    }
}

