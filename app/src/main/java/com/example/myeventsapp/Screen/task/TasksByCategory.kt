package com.example.myeventsapp.Screen.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myeventsapp.component.SearchComponent
import com.example.myeventsapp.component.TagCard
import com.example.myeventsapp.component.TaskCard
import com.example.myeventsapp.component.TasksHeaderView
import com.example.myeventsapp.data.entity.TagWithTaskLists
import com.example.myeventsapp.navigation.Screens

@Composable
fun TasksByCategory(
    navController: NavHostController,
    taskViewModel: TaskViewModel,
    tag: String?
) {
    LaunchedEffect(Unit) {
        taskViewModel.getTasksByTagName(tag.orEmpty())
    }
    val results = taskViewModel.taskWithTags.value
    Column(modifier = Modifier.padding(16.dp)) {

        TasksHeaderView(tag.orEmpty()) {
            navController.popBackStack()
        }
        SearchComponent {
            taskViewModel.searchInTasksAndTags(it)
        }

        Spacer(modifier = Modifier.size(18.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(results) {

                TaskCard(
                    taskTitle = it.task.title.orEmpty(),
                    it.task.timeFrom,
                    timeTo = it.task.timeTo,
                    tag = it.tags.filter { it.name == tag.orEmpty() },
                    onDelete = {
                    },
                    onClick = {
                        navController.navigate("${Screens.MainApp.UpdateTask.route}/${it?.task?.taskId}")
                    })

            }
        }
    }
}