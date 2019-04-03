package com.joaomgcd.taskerpluginsample.tasker.helloworld

import android.content.Context
import com.joaomgcd.taskerpluginlibrary.action.TaskerPluginRunnerActionNoOutputOrInput
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelperNoOutputOrInput
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResult
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultSucess
import com.joaomgcd.taskerpluginsample.tasker.ActivityConfigTaskerNoOutputOrInput
import com.joaomgcd.taskerpluginsample.toToast


class HelloWorldRunner : TaskerPluginRunnerActionNoOutputOrInput() {
    override fun run(context: Context, input: TaskerInput<Unit>): TaskerPluginResult<Unit> {
        "Hello Tasker World".toToast(context)
        return TaskerPluginResultSucess()
    }
}

class HelloWorldHelper(config: TaskerPluginConfig<Unit>) : TaskerPluginConfigHelperNoOutputOrInput<HelloWorldRunner>(config) {
    override val runnerClass = HelloWorldRunner::class.java
    override fun addToStringBlurb(input: TaskerInput<Unit>, blurbBuilder: StringBuilder) {
        blurbBuilder.append("This will show a toast saying 'Hello Tasker World'")
    }
}

class HelloWorldActivity : ActivityConfigTaskerNoOutputOrInput<HelloWorldRunner, HelloWorldHelper>() {
    override fun getNewHelper(config: TaskerPluginConfig<Unit>) = HelloWorldHelper(config)

}
