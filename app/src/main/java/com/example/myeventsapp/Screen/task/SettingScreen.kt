package com.example.myeventsapp.Screen.task


import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.myeventsapp.component.TasksHeaderView
import java.util.Locale







//@Composable
//fun SettingScreen(navController: NavHostController) {
//    val defaultLocale = remember { mutableStateOf(Locale.getDefault()) }
//    val isEnglish = remember { mutableStateOf(defaultLocale.value.language == "en") }
//    val currentLocale = rememberUpdatedState(if (isEnglish.value) Locale("en") else Locale("ar"))
//
//    val context = LocalContext.current
//    val localizedContext = LocaleUtils.setLocale(context, currentLocale.value)
//    val configuration = LocalConfiguration.current
//
//    Column {
//        TasksHeaderView("Settings") {
//            navController.popBackStack()
//        }
//
//        CompositionLocalProvider(LocalContext provides localizedContext) {
//            val checkedState = remember { mutableStateOf(isEnglish.value) }
//
//            Switch(
//                checked = checkedState.value, onCheckedChange = { isChecked ->
//                    checkedState.value = isChecked
//                    val newLocale = if (isChecked) Locale("en") else Locale("ar")
//                    isEnglish.value = isChecked
//                    LocaleUtils.setLocale(context, newLocale)
//                    navController.popBackStack()
//                },
//                thumbContent = {
//                    Text(if (checkedState.value) "EN" else "AR")
//                }
//            )
//            Text(text = if (checkedState.value) "Language is English" else "Language is Arabic")
//        }
//    }
//}
//


@Composable
fun SettingScreen(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("Settings", Activity.MODE_PRIVATE)
    val checkedState = remember {
        mutableStateOf(sharedPreferences.getString("My_Lang", "en") ?: "en")
    }

    Column {
        TasksHeaderView("Settings") {
            navController.popBackStack()
        }

        Switch(
            checked = checkedState.value == "en",
            onCheckedChange = { isChecked ->
                checkedState.value = if (isChecked) "en" else "ar"
                val locale = Locale(checkedState.value)
                LocaleUtils.setLocaleLang(context, locale)
                navController.popBackStack()
            },
            thumbContent = {
                Text(text = checkedState.value)
            }
        )
    }
}

object LocaleUtils {
        fun setLocaleLang(context: Context, locale: Locale): Context {
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)
        return context.createConfigurationContext(configuration)
    }

}
