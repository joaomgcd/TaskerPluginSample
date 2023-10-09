package com.joaomgcd.taskerpluginsample.tasker.backgroundwork

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.os.Bundle
import android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION
import android.text.InputType
import android.widget.EditText
import com.joaomgcd.taskerpluginlibrary.action.TaskerPluginRunnerActionNoInput
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelperNoInput
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigNoInput
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputObject
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputVariable
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResult
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultSucess
import com.joaomgcd.taskerpluginsample.InputDialog
import com.joaomgcd.taskerpluginsample.R
import com.joaomgcd.taskerpluginsample.canDrawOverlays
import com.joaomgcd.taskerpluginsample.tasker.ActivityConfigTaskerNoInput
import com.joaomgcd.taskerpluginsample.tasker.basic.BasicActionHelper
import com.joaomgcd.taskerpluginsample.toToast
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consume
import kotlinx.coroutines.flow.callbackFlow
import java.lang.RuntimeException
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


private val channelResult = Channel<String>()

class ActivityBackgroundWork : Activity(), CoroutineScope by MainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launch {
            val result = InputDialog(this@ActivityBackgroundWork, "Write Something Below").show()
            channelResult.send(result)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancel()
    }
}


@TaskerOutputObject()
class BackgroundWorkOutput(
    @get:TaskerOutputVariable("time_taken", labelResIdName = "time_taken", htmlLabelResIdName = "time_taken_description") var timeTaken: Long?,
    @get:TaskerOutputVariable("result", labelResIdName = "result", htmlLabelResIdName = "result_description") var result: String?
)

class BackgroundWorkRunner : TaskerPluginRunnerActionNoInput<BackgroundWorkOutput>() {
    override fun run(context: Context, input: TaskerInput<Unit>): TaskerPluginResult<BackgroundWorkOutput> {
        if (!context.canDrawOverlays) throw RuntimeException("Have to be able to draw overlays to launch activities from background")

        val output = runBlocking {
            val start = System.currentTimeMillis()
            context.startActivity(Intent(context, ActivityBackgroundWork::class.java).apply {
                flags = FLAG_ACTIVITY_NEW_TASK
            })
            val result = channelResult.receive()
            val end = System.currentTimeMillis()
            end - start
            BackgroundWorkOutput(end - start, result)
        }
        return TaskerPluginResultSucess(output)
    }
}

class BackgroundWorkHelper(config: TaskerPluginConfig<Unit>) : TaskerPluginConfigHelperNoInput<BackgroundWorkOutput, BackgroundWorkRunner>(config) {
    override val runnerClass = BackgroundWorkRunner::class.java
    override val outputClass get() = BackgroundWorkOutput::class.java
    override fun addToStringBlurb(input: TaskerInput<Unit>, blurbBuilder: StringBuilder) {
        blurbBuilder.append("Will ask for some input and return it in the result variables below")
    }

}

/**
 * A plugin activity (in this case the ActivityBackgroundWork class above) has to be able to be launched from the background and for that we need the "android.permission.SYSTEM_ALERT_WINDOW" permission which is requested below.
 */
class BackgroundWorkActivity : Activity(), TaskerPluginConfigNoInput {
    override val context get() = applicationContext
    private val taskerHelper by lazy { BackgroundWorkHelper(this) }
    private val permissionRequestCode = 12
    private fun finishForTasker() {
        taskerHelper.finishForTasker()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (canDrawOverlays) return finishForTasker()

        "Enable \"Tasker Plugin Sample\" on this list".toToast(this)
        val uri = Uri.parse("package:$packageName")
        val intent = Intent(ACTION_MANAGE_OVERLAY_PERMISSION).setData(uri)
        startActivityForResult(intent, permissionRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (!canDrawOverlays) "Permission not yet granted!".toToast(this)
        finishForTasker()
    }
}
