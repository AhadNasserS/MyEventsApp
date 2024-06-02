package com.example.myeventsapp.Screen.task

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myeventsapp.data.entity.Tags
import com.example.myeventsapp.data.entity.Task
import com.example.myeventsapp.data.entity.TaskTagCrossRef
import com.example.myeventsapp.data.entity.TaskType
import com.example.myeventsapp.data.repositry.TaskRepositry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddTaskViewModel @Inject constructor(private val taskRepositry: TaskRepositry) : ViewModel() {

    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")
    val taskDate: MutableState<String> = mutableStateOf("")
    val startTime: MutableState<String> = mutableStateOf("")
    val endTime: MutableState<String> = mutableStateOf("")
    private val taskType: MutableState<String> = mutableStateOf(TaskType.OnGoing.type)
    private val category: MutableState<String> = mutableStateOf("")

    val tagName: MutableState<String> = mutableStateOf("")
    val tagColor: MutableState<String> = mutableStateOf("")
    val tagIcon: MutableState<String> = mutableStateOf("")

    val allTags = taskRepositry.getAllTags()

    val selectedTags = mutableStateOf<Set<Tags>>(emptySet())

    fun addTask() {

        viewModelScope.launch {
            val task = Task(
                title = title.value,
                description = description.value,
                date = taskDate.value,
                timeFrom = startTime.value,
                timeTo = endTime.value,
                taskType = taskType.value,
                tagName = category.value
            )
            insertTaskWithTags(
                task,
                selectedTags.value.toList()
            )
        }
    }

    fun addTag() {
        //todo add validation for tagName field
        viewModelScope.launch {
            taskRepositry.insertTag(
                Tags(
                    tagName.value,
                    tagColor.value,
                    tagIcon.value,
                    isSelected = true
                )
            )
        }
    }

    private suspend fun insertTaskWithTags(task: Task, tags: List<Tags>) {
        val taskId = taskRepositry.insertTask(task) // Insert the task and get its generated ID
        val taskTagCrossRefs =
            tags.map { TaskTagCrossRef(taskId, it.name) } // Create TaskTagCrossRef objects
        taskRepositry.insertTaskTagCrossRefs(taskTagCrossRefs) // Insert TaskTagCrossRef objects to associate tags with the task
    }

}
