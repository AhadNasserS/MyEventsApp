package com.example.myeventsapp.ui

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.myeventsapp.Screen.auth.AuthViewModel
import com.example.myeventsapp.navigation.EventsAppNavigation
import com.example.myeventsapp.navigation.Screens
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class TestNavigation {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()
    private  lateinit var authViewModel: AuthViewModel
    private lateinit var navController: TestNavHostController

    @Before
    fun init(){
        hiltRule.inject()
        composeTestRule.setContent {
            authViewModel = hiltViewModel()
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
                navigatorProvider.addNavigator(DialogNavigator())

            }
            EventsAppNavigation(navController= navController, authViewModel = authViewModel )
        }
    }

    @Test
    fun testStartDestination(){
        if (authViewModel.auth.currentUser != null){
            assertEquals(
                Screens.MainApp.Home.route,
                navController.currentBackStackEntry?.destination?.route
            )
        } else{
            assertEquals(
                Screens.Authentication.Splash.route,
                navController.currentBackStackEntry?.destination?.route
            )
        }
    }

    @Test
    fun testStartDestinationIfUserLoggedIn(){
        assertEquals(
            authViewModel.isSignIn.value,
            navController.currentBackStackEntry?.destination?.parent?.route
        )
    }
}