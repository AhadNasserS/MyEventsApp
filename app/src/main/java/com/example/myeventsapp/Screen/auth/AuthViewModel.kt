package com.example.myeventsapp.Screen.auth

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myeventsapp.navigation.Screens
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel(){
    var auth = Firebase.auth
    var isSignIn =
        if (auth.currentUser == null ) mutableStateOf(Screens.Authentication.route) else mutableStateOf(
            Screens.MainApp.route
        )
        val error = mutableStateOf("")


    init{
        auth.apply {
            this.addAuthStateListener {
                isSignIn.value =
                if(it.currentUser == null) Screens.Authentication.route else Screens.MainApp.route
            }
        }
    }


    fun login(emil: String , password: String){
        auth.signInWithEmailAndPassword(emil , password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                } else {
                    error.value = task.exception?.message.orEmpty()
                }
            }
    }
    fun logout(context: Context) {
        auth.signOut()
        GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        ).signOut()
    }

    fun signup(emil: String, password: String){
        auth.createUserWithEmailAndPassword(emil,password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful) {
                } else {
                    val  exception = task.exception
                    error.value = task.exception?.message.orEmpty()
                }
            }

    }

    fun restPassword(emil: String){
        auth.sendPasswordResetEmail(emil)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){

            } else {

                    error.value = task.exception?.message.orEmpty()
                }
            }
    }
}