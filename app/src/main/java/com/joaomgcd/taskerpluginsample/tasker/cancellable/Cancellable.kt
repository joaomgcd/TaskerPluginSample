package com.joaomgcd.taskerpluginsample.tasker.cancellable

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Build
import com.joaomgcd.taskerpluginlibrary.action.IntentServiceAction
import com.joaomgcd.taskerpluginlibrary.action.TaskerPluginRunnerActionNoOutputOrInput
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelperNoOutputOrInput
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResult
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultSucess
import com.joaomgcd.taskerpluginsample.R
import com.joaomgcd.taskerpluginsample.tasker.ActivityConfigTaskerNoOutputOrInput


class CancellableRunner : TaskerPluginRunnerActionNoOutputOrInput() {
    override val notificationProperties get() = NotificationProperties(iconResId = R.drawable.plugin) { context ->
        val intent = Intent(context, IntentServiceAction::class.java).apply {
            action = IntentServiceAction.ACTION_STOP
        }
        val pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val text = context.getString(R.string.cancel)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val action = Notification.Action.Builder(Icon.createWithResource(context, R.mipmap.ic_launcher), text, pendingIntent).build()
            addAction(action)
        } else {
            addAction(R.mipmap.ic_launcher, text, pendingIntent)
        }
    }

    override fun run(context: Context, input: TaskerInput<Unit>): TaskerPluginResult<Unit> {
        Thread.sleep(10000)
        return TaskerPluginResultSucess()
    }
}

class CancellableHelper(config: TaskerPluginConfig<Unit>) : TaskerPluginConfigHelperNoOutputOrInput<CancellableRunner>(config) {
    override val runnerClass = CancellableRunner::class.java
    override fun addToStringBlurb(input: TaskerInput<Unit>, blurbBuilder: StringBuilder) {
        blurbBuilder.append("This will start a task that takes 10 seconds that can be cancelled from its Notification.")
    }
}

class CancellableActivity : ActivityConfigTaskerNoOutputOrInput<CancellableRunner, CancellableHelper>() {
    override fun getNewHelper(config: TaskerPluginConfig<Unit>) = CancellableHelper(config)
}
