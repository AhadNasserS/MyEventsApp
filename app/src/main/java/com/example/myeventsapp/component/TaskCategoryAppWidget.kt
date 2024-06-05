package com.example.myeventsapp.component


import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import com.example.myeventsapp.R
import com.example.myeventsapp.data.entity.TaskType
import com.example.myeventsapp.data.repositry.TaskRepositry
import com.example.myeventsapp.ui.theme.PrimaryColor
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject


class TaskCategoryAppWidget : GlanceAppWidget() {

    @Composable
    fun Content() {
        val context = LocalContext.current

        val viewModel =
            EntryPoints.get(
                context,
                FavoriteAppWidgetEntryPoint::class.java,
            ).getViewModel()

        val tasks = viewModel.onGoingTasks().collectAsState(null)


        TaskCategoryCardWidgetContent(
            title = "On Going ",
            subTitle =tasks.value?.tasks?.size.toString() + " Tasks",
            tintColor = Color(tasks.value?.tag?.color?.toIntOrNull() ?: PrimaryColor.toArgb()),
            onClick = {

            },
            imageResId = R.drawable.imac_2
        )


    }

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Content()
        }
    }
    @EntryPoint
    @InstallIn(SingletonComponent::class)
    fun interface FavoriteAppWidgetEntryPoint {

        fun getViewModel(): TaskAppWidgetViewModel
    }
}

class TaskCategoryAppWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget
        get() = TaskCategoryAppWidget()


}

class TaskAppWidgetViewModel
@Inject
constructor(
    private val appsRepositry: TaskRepositry
) {
    fun onGoingTasks() = appsRepositry.getTagWithTasksList(TaskType.OnGoing.type)
}