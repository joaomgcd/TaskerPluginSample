package com.joaomgcd.taskerpluginsample.tasker.togglecondition

import android.content.Context
import com.joaomgcd.taskerpluginlibrary.action.TaskerPluginRunnerActionNoOutputOrInput
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelperNoOutputOrInput
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResult
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultSucess
import com.joaomgcd.taskerpluginsample.tasker.ActivityConfigTaskerNoOutputOrInput
import com.joaomgcd.taskerpluginsample.tasker.togglingcondition.TogglingConditionRunner


class ToggleConditionRunner : TaskerPluginRunnerActionNoOutputOrInput() {
    override fun run(context: Context, input: TaskerInput<Unit>): TaskerPluginResult<Unit> {
        TogglingConditionRunner.toggle(context)
        return TaskerPluginResultSucess()
    }
}

class ToggleConditionHelper(config: TaskerPluginConfig<Unit>) : TaskerPluginConfigHelperNoOutputOrInput<ToggleConditionRunner>(config) {
    override val runnerClass = ToggleConditionRunner::class.java
    override fun addToStringBlurb(input: TaskerInput<Unit>, blurbBuilder: StringBuilder) {
        blurbBuilder.append("This will toggle this plugin's 'Toggling' condition")
    }
}

class ToggleConditionActivity : ActivityConfigTaskerNoOutputOrInput<ToggleConditionRunner, ToggleConditionHelper>() {
    override fun getNewHelper(config: TaskerPluginConfig<Unit>) = ToggleConditionHelper(config)

}
