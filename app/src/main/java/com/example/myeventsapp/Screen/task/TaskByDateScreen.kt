package com.example.myeventsapp.Screen.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myeventsapp.component.TaskCard
import java.time.LocalDate
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.myeventsapp.R
import com.example.myeventsapp.component.CalendarWeeklyView
import com.example.myeventsapp.component.SearchComponent
import com.example.myeventsapp.navigation.Screens

@Composable
fun TaskByDateScreen(viewmodel: TaskViewModel, navController: NavHostController) {

    LaunchedEffect(Unit) {
        viewmodel.sortTasksByDate(LocalDate.now().toString())

    }
    SearchComponent {
        viewmodel.searchInTasksAndTags(it)
    }

    Spacer(modifier = Modifier.size(20.dp))

    val tasks = viewmodel.taskWithTags.value

    var selectedDate by remember {
        mutableStateOf(LocalDate.now().toString())
    }
    LazyColumn(
        modifier = Modifier
            .padding(16.dp)
            .padding(bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            CalendarWeeklyView(onDateSelected = {
                viewmodel.sortTasksByDate(it.toString())
                selectedDate = it.toString()
            })
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = if (selectedDate == LocalDate.now()
                            .toString()
                    ) "Today" else selectedDate,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "09 h 45 min")
            }
        }
        val groupedList = tasks.groupBy { it.task.timeFrom }

        if (groupedList.isNotEmpty()) {
            groupedList.forEach {

                item {
                    LazyRow(
                        modifier = Modifier.fillParentMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Text(text = it.key.orEmpty())
                        }
                        items(it.value) { taskWithTags ->
                            Box(modifier = Modifier.fillParentMaxWidth(0.6f)) {
                                TaskCard(
                                    taskTitle = taskWithTags.task.title,
                                    taskWithTags.task.timeFrom,
                                    taskWithTags.task.timeTo,
                                    taskWithTags.tags,
                                    onDelete = {
                                        viewmodel.deleteTask(taskWithTags.task)
                                    },
                                    onClick = {
                                        navController.navigate("${Screens.MainApp.UpdateTask.route}/${taskWithTags.task.taskId}")
                                    }
                                )
                            }
                        }
                    }
                }
            }
        } else {
            item {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.layer_2),
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier.padding(vertical = 16.dp),
                        text = "You don’t have any schedule today.\n" +
                                "Tap the plus button to create new to-do.",
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }

            }
        }

    }
}