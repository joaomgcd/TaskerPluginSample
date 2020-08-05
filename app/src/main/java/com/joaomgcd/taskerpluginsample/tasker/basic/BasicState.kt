package com.joaomgcd.taskerpluginsample.tasker.basic

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.joaomgcd.taskerpluginlibrary.condition.TaskerPluginRunnerConditionNoOutputOrInputOrUpdateState
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelperStateNoOutputOrInputOrUpdate
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigNoInput
import com.joaomgcd.taskerpluginlibrary.extensions.requestQuery
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultCondition
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultConditionSatisfied
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultConditionUnsatisfied
import com.joaomgcd.taskerpluginsample.tasker.togglingcondition.TogglingConditionRunner


class BasicStateHelper(config: TaskerPluginConfig<Unit>) : TaskerPluginConfigHelperStateNoOutputOrInputOrUpdate<BasicStateRunner>(config) {
    override val runnerClass get() = BasicStateRunner::class.java
    override fun addToStringBlurb(input: TaskerInput<Unit>, blurbBuilder: StringBuilder) {
        blurbBuilder.append("Will toggle between being activated and deactivated")
    }
}

class ActivityConfigBasicState : Activity(), TaskerPluginConfigNoInput {
    override val context: Context get() = applicationContext
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BasicStateHelper(this).finishForTasker()
    }
}

class BasicStateRunner : TaskerPluginRunnerConditionNoOutputOrInputOrUpdateState() {
    override fun getSatisfiedCondition(context: Context, input: TaskerInput<Unit>, update: Unit?): TaskerPluginResultCondition<Unit> {
        return if (TogglingConditionRunner.isOn) TaskerPluginResultConditionSatisfied(context) else TaskerPluginResultConditionUnsatisfied()
    }
}

private var inActive = false
fun Context.toggleBasicState() {
    inActive = !inActive
    ActivityConfigBasicState::class.java.requestQuery(this)
}