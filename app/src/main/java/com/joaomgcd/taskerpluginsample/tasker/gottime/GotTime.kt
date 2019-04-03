package com.joaomgcd.taskerpluginsample.tasker.gottime

import android.content.Context
import com.joaomgcd.taskerpluginlibrary.condition.TaskerPluginRunnerConditionEvent
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputField
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputRoot
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputObject
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultCondition
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultConditionSatisfied


@TaskerInputRoot
class GotTimeInput()

@TaskerOutputObject
class GotTimeOutput()

@TaskerInputRoot
class GotTimeUpdate @JvmOverloads constructor(@field:TaskerInputField("time") var time: Long? = null)

class GotTimeActionRunner() : TaskerPluginRunnerConditionEvent<GotTimeInput, GotTimeOutput, GotTimeUpdate>() {
    override fun getSatisfiedCondition(context: Context, input: TaskerInput<GotTimeInput>, update: GotTimeUpdate?): TaskerPluginResultCondition<GotTimeOutput> {
        return TaskerPluginResultConditionSatisfied(context)
    }


}