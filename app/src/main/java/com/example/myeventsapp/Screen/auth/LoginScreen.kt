package com.example.myeventsapp.Screen.auth


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myeventsapp.R
import com.example.myeventsapp.component.LoginWithGoogle
import com.example.myeventsapp.navigation.Screens


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController,
                viewModel: AuthViewModel) {


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(start = 50.dp, top = 110.dp, bottom = 50.dp)
        )
        var userEmail by remember {
            mutableStateOf("")
        }
        var userPassword by remember {
            mutableStateOf("")
        }

        TextField(
            value = userEmail,
            onValueChange = { userEmail = it },
            label = {
                Text(
                    text = "Email ID or Username",
                    style = TextStyle(color = Color.Gray),
                    textAlign = TextAlign.Start
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.message),
                    contentDescription = "login icon "
                )
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 80.dp)
                .fillMaxWidth(0.90f)
                .background(Color.White),
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Gray
            )
        )
        TextField(
            value = userPassword,
            onValueChange = { userPassword = it },
            label = { Text(text = "Password", style = TextStyle(color = Color.Gray)) },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.password_icon),
                    contentDescription = "password icon"
                )
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 20.dp)
                .fillMaxWidth(0.90f)
                .background(Color.White),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Gray
            )

        )

        Text(text = "forget password?", style = TextStyle(Color.Blue), modifier = Modifier
            .padding(start = 230.dp, bottom = 80.dp)
            .clickable { viewModel.restPassword(userEmail) })

        Button(
            onClick = {
                viewModel.login(userEmail, userPassword)

            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .align(Alignment.CenterHorizontally)
                .size(50.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)

        ) {
            Text(text = "Login")
        }
        Text(
            text = "or with", modifier = Modifier
                .padding(top = 50.dp)
                .align(Alignment.CenterHorizontally),
            color = Color.Gray
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth(0.70f)
                .align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.primaryContainer
        )
        LoginWithGoogle()

        Text(
            text = "Don’t have an account? Sign Up ",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable {navController.navigate(Screens.Authentication.SignUp.route) }
                .align(Alignment.CenterHorizontally)
        )

    }

}


