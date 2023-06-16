package com.joaomgcd.taskerpluginsample.tasker.togglingcondition

import android.view.LayoutInflater
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelper
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginsample.R
import com.joaomgcd.taskerpluginsample.databinding.ActivityConfigTogglingConditionBinding
import com.joaomgcd.taskerpluginsample.tasker.ActivityConfigTasker


class TogglingConditionHelper(config: TaskerPluginConfig<TogglingConditionInput>) : TaskerPluginConfigHelper<TogglingConditionInput, TogglingConditionOutput, TogglingConditionRunner>(config) {
    override val runnerClass = TogglingConditionRunner::class.java
    override val inputClass = TogglingConditionInput::class.java
    override val outputClass = TogglingConditionOutput::class.java
}

class ActivityConfigTogglingCondition : ActivityConfigTasker<TogglingConditionInput, TogglingConditionOutput, TogglingConditionRunner, TogglingConditionHelper, ActivityConfigTogglingConditionBinding>() {

    override fun assignFromInput(input: TaskerInput<TogglingConditionInput>) {
    }

    override fun getNewHelper(config: TaskerPluginConfig<TogglingConditionInput>) = TogglingConditionHelper(config)
    override val inputForTasker = TaskerInput(TogglingConditionInput())
    override fun inflateBinding(layoutInflater: LayoutInflater) = ActivityConfigTogglingConditionBinding.inflate(layoutInflater)

}