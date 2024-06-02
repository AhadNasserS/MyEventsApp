package com.example.myeventsapp

import android.content.Intent
import androidx.compose.ui.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.myeventsapp.Screen.auth.AuthViewModel
import com.example.myeventsapp.component.BottomBar
import com.example.myeventsapp.navigation.EventsAppNavigation
import com.example.myeventsapp.navigation.Screens
import com.example.myeventsapp.ui.theme.MyEventsAppTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            val authViewModel: AuthViewModel = hiltViewModel()
            MyEventsAppTheme {
                val navController = rememberNavController()
                val config = LocalConfiguration.current
                var showBottomBar by rememberSaveable { mutableStateOf(false) }
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                showBottomBar = when (navBackStackEntry?.destination?.route) {
                    Screens.MainApp.Home.route -> true
                    Screens.MainApp.TaskByDate.route -> true
                    Screens.MainApp.CategoryScreen.route -> true
                    Screens.MainApp.StaticsScreen.route -> true
                    else -> false
                }
                CompositionLocalProvider(
                    LocalLayoutDirection provides
                            if (config.layoutDirection == LayoutDirection.Rtl.ordinal)
                                LayoutDirection.Rtl
                            else LayoutDirection.Ltr
                ) {
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize()
                            .semantics {
                                contentDescription = "MyScreen"
                            },
                    ) { paddingValues ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        ) {

                            if (authViewModel.error.value.isNotEmpty()) {
                                Snackbar(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth(),
//                                    containerColor = Color.Red.copy(0.5f)
                                ) {
                                    Text(
                                        text = authViewModel.error.value
                                    )
                                }
                            }
                            EventsAppNavigation(authViewModel, navController)
                        }
                        if (showBottomBar) {
                            BottomBar(navController)
                        }
                    }
                }
            }
        }
    }
}