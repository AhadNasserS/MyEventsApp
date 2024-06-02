package com.example.myeventsapp.Screen.task

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myeventsapp.data.entity.TagWithTaskLists
import com.example.myeventsapp.data.entity.Tags
import com.example.myeventsapp.data.entity.Task
import com.example.myeventsapp.data.entity.TaskTagCrossRef
import com.example.myeventsapp.data.entity.TaskType
import com.example.myeventsapp.data.entity.TaskWithTags
import com.example.myeventsapp.data.repositry.TaskRepositry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepositry: TaskRepositry
) : ViewModel() {

    val cancelledTasks = taskRepositry.getTagWithTasksList(TaskType.Cancelled.type)
    val pendingTasks = taskRepositry.getTagWithTasksList(TaskType.Pending.type)
    val completedTasks = taskRepositry.getTagWithTasksList(TaskType.Completed.type)
    val onGoingTasks = taskRepositry.getTagWithTasksList(TaskType.OnGoing.type)

    val tagWithTasks = MutableStateFlow<List<TagWithTaskLists>>(emptyList())
    val taskWithTags : MutableState<List<TaskWithTags>> = mutableStateOf(emptyList())

    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")
    val taskDate: MutableState<String> = mutableStateOf("")
    val startTime: MutableState<String> = mutableStateOf("")
    val endTime: MutableState<String> = mutableStateOf("")
    private val taskType: MutableState<String> = mutableStateOf("")
    private val category: MutableState<String> = mutableStateOf("")

    //tag
    val tagName: MutableState<String> = mutableStateOf("")
    val tagColor: MutableState<String> = mutableStateOf("")
    val tagIcon: MutableState<String> = mutableStateOf("")
    val isSelected: MutableState<Boolean> = mutableStateOf(false)

    val selectedTags = mutableStateOf<List<Tags>>(emptyList())

    var allTags: MutableStateFlow<List<Tags>> = MutableStateFlow(emptyList())

    val taskInWeek = taskRepositry.getTasksWithTagsByDayOfCurrentWeek()


    init {
        //add base tags
        viewModelScope.launch {
            val tagsList = TaskType.entries.map {
                Tags(it.type, it.color, it.icon, it.isSelected == true)
            }
            taskRepositry.insertTagList(tagsList)
            taskRepositry.getAllTags().collect{
                allTags.value =it
            }
        }
        getTagWithTaskLists()
    }

    fun addTask() {
        val task = Task(
            title = title.value,
            description = description.value,
            date = taskDate.value,
            timeFrom = startTime.value,
            timeTo = endTime.value,
            taskType = taskType.value,
            tagName = category.value
        )
        viewModelScope.launch {
            insertTaskWithTags(
                task,
                selectedTags.value
            )

        }
    }

    fun getSelectedTask(taskId: Long) {
        viewModelScope.launch {
            val selectedTask = taskRepositry.getTaskWithTagsById(taskId)

            title.value = selectedTask.task.title
            description.value = selectedTask.task.description
            taskDate.value = selectedTask.task.date
            startTime.value = selectedTask.task.timeFrom.orEmpty()
            endTime.value = selectedTask.task.timeTo.orEmpty()
            selectedTags.value = selectedTask.tags //all tags is selected false
        }
    }

    fun updateTask(taskId: Long) {
        viewModelScope.launch {

            val task = Task(
                taskId = taskId,
                title = title.value,
                description = description.value,
                date = taskDate.value,
                timeFrom = startTime.value,
                timeTo = endTime.value,
                taskType = taskType.value,
                tagName = tagName.value
            )

            taskRepositry.updateTaskWithTags(task, selectedTags.value)
            refreshTasks()

        }

    }

    private fun refreshTasks() {
        viewModelScope.launch {
            val tasks = taskRepositry.getAllTasksWithTags()
            taskWithTags.value = tasks
        }
    }
    fun addTag() {
        viewModelScope.launch {
            taskRepositry.insertTag(
                Tags(
                    tagName.value,
                    tagColor.value,
                    tagIcon.value,
                    isSelected.value
                )
            )

        }
    }

    private suspend fun insertTaskWithTags(task: Task, tags: List<Tags>) {
        val taskId = taskRepositry.insertTask(task) // Insert the task and get its generated ID
        taskRepositry.insertTagList(tags)
        val taskTagCrossRefs = tags.map {
            TaskTagCrossRef(taskId, it.name)
        }
        taskRepositry.insertTaskTagCrossRefs(taskTagCrossRefs)

    }


    fun sortTasksByDate(date: String) {
        viewModelScope.launch {
            taskRepositry.sortTasksByDate(date).collect {
                taskWithTags.value = it
            }
        }
    }

    private fun getTagWithTaskLists() {
        viewModelScope.launch {
            taskRepositry.getTagWithTaskLists().collect {
                tagWithTasks.value = it
            }
        }
    }

    fun getTasksByTagName(tagName: String) {
        viewModelScope.launch {
            taskRepositry.getAllTaskWithTags().collect {
                taskWithTags.value =
                    it.filter { task ->
                        task.tags.map { tag ->
                            tag.name
                        }.contains(tagName)
                    }
            }
        }
    }

    fun searchInTasksAndTags(query: String) {
        viewModelScope.launch {
            tagWithTasks.value = taskRepositry.searchCombined(query).tagResults
            taskWithTags.value = taskRepositry.searchCombined(query).taskResults
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepositry.deleteTask(task)

        }
    }

    fun getAllTag() {
        viewModelScope.launch {
            taskRepositry.getAllTags().collect {
                allTags.value = it
            }
        }
    }
}