package com.joaomgcd.taskerpluginsample.tasker.gettime

import android.app.PendingIntent
import android.content.Context
import com.joaomgcd.taskerpluginlibrary.action.TaskerPluginRunnerAction
import com.joaomgcd.taskerpluginlibrary.extensions.requestQuery
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.output.TaskerOuputBase
import com.joaomgcd.taskerpluginlibrary.runner.TaskerOutputRename
import com.joaomgcd.taskerpluginlibrary.runner.TaskerOutputRenames
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResult
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultSucess
import com.joaomgcd.taskerpluginsample.R
import com.joaomgcd.taskerpluginsample.tasker.gottime.ActivityConfigGotTime
import com.joaomgcd.taskerpluginsample.tasker.gottime.GotTimeUpdate
import java.util.*

class GetTimeRunner : TaskerPluginRunnerAction<GetTimeInput, GetTimeOutput>() {
    override val notificationProperties get() = NotificationProperties(iconResId = R.drawable.plugin)

    //User has the option to name the output variable for the time, so you add that rename here
    override fun addOutputVariableRenames(context: Context, input: TaskerInput<GetTimeInput>, renames: TaskerOutputRenames) {
        super.addOutputVariableRenames(context, input, renames)
        renames.add(TaskerOutputRename(GetTimeOutput.VAR_FORMATTED_TIME, input.regular.variableName))
    }

    override fun shouldAddOutput(context: Context, input: TaskerInput<GetTimeInput>?, ouput: TaskerOuputBase): Boolean {
        if (input == null) return true
        if (input.regular.getSeconds) return true;
        return ouput.nameNoSuffix != GetTimeOutput.VAR_SECONDS
    }

    override fun run(context: Context, input: TaskerInput<GetTimeInput>): TaskerPluginResult<GetTimeOutput> {
        //Notice how you get the dynamic input by key here
        val dateMs = input.dynamic.getByKey(KEY_TIME_CREATED)?.valueAs<Long?>()
                ?: throw RuntimeException("Time was not configured!!")
        val formatted = input.regular.format(dateMs)
        var result = formatted
        input.regular.times?.let { times ->
            for (count in 1 until times) {
                result += " $formatted"
            }
        }
        ActivityConfigGotTime::class.java.requestQuery(context, GotTimeUpdate(Date().time))
        return TaskerPluginResultSucess(GetTimeOutput(result, "Not for below Oreo 8.1!!"))
    }

}