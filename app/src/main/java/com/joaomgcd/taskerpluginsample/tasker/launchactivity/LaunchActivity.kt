package com.joaomgcd.taskerpluginsample.tasker.launchactivity

import android.content.Context
import android.net.Uri
import com.joaomgcd.taskerpluginlibrary.action.TaskerPluginRunnerActionNoOutputOrInput
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelperNoOutputOrInput
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResult
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultSucess
import com.joaomgcd.taskerpluginsample.tasker.ActivityConfigTaskerNoOutputOrInput
import com.joaomgcd.taskerpluginsample.toToast


class LaunchActivityRunner : TaskerPluginRunnerActionNoOutputOrInput() {
    override fun run(context: Context, input: TaskerInput<Unit>): TaskerPluginResult<Unit> {
        "Launching activity...".toToast(context)
        return TaskerPluginResultSucess(callbackUri = Uri.parse("taskerpluginsample://fromplugin"))
    }
}

class LaunchActivityHelper(config: TaskerPluginConfig<Unit>) : TaskerPluginConfigHelperNoOutputOrInput<LaunchActivityRunner>(config) {
    override val runnerClass = LaunchActivityRunner::class.java
    override fun addToStringBlurb(input: TaskerInput<Unit>, blurbBuilder: StringBuilder) {
        blurbBuilder.append("This will launch an activity when the plugin action is ran")
    }
}

class LaunchActivityActivity : ActivityConfigTaskerNoOutputOrInput<LaunchActivityRunner, LaunchActivityHelper>() {
    override fun getNewHelper(config: TaskerPluginConfig<Unit>) = LaunchActivityHelper(config)

}
