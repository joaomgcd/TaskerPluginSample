package com.joaomgcd.taskerpluginsample.tasker.dynamicinput

import android.content.Context
import com.joaomgcd.taskerpluginlibrary.action.TaskerPluginRunnerActionNoOutputOrInput
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelperNoOutputOrInput
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputInfo
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputInfos
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResult
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultSucess
import com.joaomgcd.taskerpluginsample.tasker.ActivityConfigTaskerNoOutputOrInput
import com.joaomgcd.taskerpluginsample.toToast

/**
 * Example of an Action that uses dynamic input just to get variable keys and values from the user
 */

/**
 * list of infos that could come from a main app. Are not related to the Tasker UI. In this list each item has a property that says if it's a Tasker value or not
 */
class InfoFromMainApp(val name: String, val hasTaskerVariable: Boolean = false)
class InfosFromMainApp : ArrayList<InfoFromMainApp>()
private val infos = InfosFromMainApp().apply {
    addAll(arrayOf(
            InfoFromMainApp("year", true),
            InfoFromMainApp("genre", false)
    ))
}

/**
 * Get all infos that are Tasker values
 */
private val infosForTasker get() = infos.filter { it.hasTaskerVariable }

/**
 * Gets all infos for Tasker, gets their value from the dynamic input and builds a string with the key and value for each info
 */
class DynamicInputRunner : TaskerPluginRunnerActionNoOutputOrInput() {
    override fun run(context: Context, input: TaskerInput<Unit>): TaskerPluginResult<Unit> {
        StringBuilder().apply {
            infosForTasker.forEach {
                input.dynamic.getByKey(it.name)?.let { append("${it.key}:${it.value}").append("\n") }
            }
        }.toString().toToast(context)

        return TaskerPluginResultSucess()
    }
}

/**
 * Very basic. Only shows the user that info will be shown
 */
class DynamicInputHelper(config: TaskerPluginConfig<Unit>) : TaskerPluginConfigHelperNoOutputOrInput<DynamicInputRunner>(config) {
    override val runnerClass = DynamicInputRunner::class.java
    override fun addToStringBlurb(input: TaskerInput<Unit>, blurbBuilder: StringBuilder) {
        blurbBuilder.insert(0,"This will show info about inserted Variables\n")
    }

}

/**
 * Adds all infos that are for Tasker to the dynamic Input.
 * In this case the value is just a Tasker variable with the same name as the info name.
 * In an actual scenario the user could input the variable name him/herself
 */
class DynamicInputActivity : ActivityConfigTaskerNoOutputOrInput<DynamicInputRunner, DynamicInputHelper>() {
    override fun getNewHelper(config: TaskerPluginConfig<Unit>) = DynamicInputHelper(config)
    override val inputForTasker = TaskerInput(Unit, TaskerInputInfos().apply {
        infosForTasker
                .forEach { add(TaskerInputInfo(it.name,it.name,null,false,"%${it.name}")) }

    })
}
