package com.example.myeventsapp.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import com.example.myeventsapp.data.entity.TaskType
import com.example.myeventsapp.navigation.Screens

//@Composable
//fun DropDownMenu(navController: NavController) {
//    var expanded by remember { mutableStateOf(true) }
//    val items = mapOf(
//        Icons.Outlined.Done to "Enable",
//        Icons.Outlined.Edit to "Edit",
//        Icons.Outlined.Delete to "Delete",
//    )
//    var selectedIndex by remember { mutableStateOf(0) }
//
//    Box(modifier = Modifier
//        .wrapContentSize(Alignment.TopStart)
//        .background(Color.White)) {
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false
//                navController.popBackStack()},
//            offset = DpOffset(45.dp, 24.dp),
//            properties = PopupProperties(dismissOnClickOutside = true, dismissOnBackPress = true),
//            modifier = Modifier
//                .background(
//                    Color.White
//                )
//        ) {
//            items.entries.forEachIndexed { index, element ->
//                DropdownMenuItem(
//                    text = { Text(text = element.value) },
//                    onClick = {
//                        selectedIndex = index
//                        expanded = false
//                    },
//                    leadingIcon = {
//                        Icon(
//                            imageVector = element.key,
//                            contentDescription = null
//                        )
//                    }
//                )
//            }
//        }
//    }
//}


@Composable
fun DropDownMenu(navController: NavController) {
    var expanded by remember { mutableStateOf(true) }
    val items = mapOf(
        Icons.Outlined.Done to "Enable",
        Icons.Outlined.Edit to "Edit",
        Icons.Outlined.Delete to "Delete"
    )
    var selectedIndex by remember { mutableStateOf(0) }

    Box(modifier = Modifier
        .wrapContentSize(Alignment.TopStart)
        .background(Color.White)) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false
                navController.popBackStack() },
            offset = DpOffset(45.dp, 24.dp),
            properties = PopupProperties(dismissOnClickOutside = true, dismissOnBackPress = true),
            modifier = Modifier.background(Color.White)
        ) {
            items.entries.forEachIndexed { index, element ->
                DropdownMenuItem(
                    text = { Text(text = element.value) },
                    onClick = {
                        selectedIndex = index
                        expanded = false
                        when (element.value) {
                            "Enable" -> {
//                          ////////
//                                enableFunction()
                            }
                            "Edit" -> {
//                                navController.navigate("AddTaskScreen/$taskId")
                                navController.navigate(Screens.MainApp.AddTaskScreen.route)
                            }
                            "Delete" -> {

                                deleteFunction()
                            }
                        }
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = element.key,
                            contentDescription = null
                        )
                    }
                )
            }
        }
    }
}

//fun enableFunction() {
//}

fun deleteFunction() {
    println("Task has been deleted.")
}



