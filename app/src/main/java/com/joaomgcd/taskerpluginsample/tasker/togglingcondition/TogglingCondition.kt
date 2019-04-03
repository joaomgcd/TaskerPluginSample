package com.joaomgcd.taskerpluginsample.tasker.togglingcondition

import android.content.Context
import com.joaomgcd.taskerpluginlibrary.condition.TaskerPluginRunnerConditionState
import com.joaomgcd.taskerpluginlibrary.extensions.requestQuery
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputRoot
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultCondition
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultConditionSatisfied
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultConditionUnsatisfied


@TaskerInputRoot
class TogglingConditionInput

class TogglingConditionOutput


class TogglingConditionRunner() : TaskerPluginRunnerConditionState<TogglingConditionInput, TogglingConditionOutput>() {

    override fun getSatisfiedCondition(context: Context, input: TaskerInput<TogglingConditionInput>, update: Unit?): TaskerPluginResultCondition<TogglingConditionOutput> {
        return if (isOn) TaskerPluginResultConditionSatisfied(context) else TaskerPluginResultConditionUnsatisfied()
    }

    companion object {
        var isOn = false
        fun toggle(context: Context) {
            isOn = !isOn
            ActivityConfigTogglingCondition::class.java.requestQuery(context)
        }
    }
}