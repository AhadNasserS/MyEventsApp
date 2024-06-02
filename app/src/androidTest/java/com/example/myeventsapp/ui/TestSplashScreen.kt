package com.example.myeventsapp.ui

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.myeventsapp.MainActivity
import com.example.myeventsapp.Screen.auth.AuthViewModel
import com.example.myeventsapp.Screen.auth.SplashScreen
import com.example.myeventsapp.navigation.EventsAppNavigation
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class TestSplashScreen {
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    private lateinit var navController : TestNavHostController

    @Before
    fun init(){
        hiltRule.inject()
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {

            }
            SplashScreen(navController = navController)
        }
    }

    @Test
    fun testSplashScreen(){
        composeTestRule.onNodeWithTag("SplashScreen").assertIsDisplayed()
    }
    @Test
    fun testSplashScreenContent(){
        composeTestRule.onNodeWithTag("into image").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("title text").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("description text").assertIsDisplayed()

    }

    @Test
    fun testSplashScreenClickButton(){
        composeTestRule.onNodeWithTag("Login Button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("Login Button").assertHasClickAction()
    }
}