package com.joaomgcd.taskerpluginsample.tasker.togglingcondition

import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelper
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginsample.R
import com.joaomgcd.taskerpluginsample.tasker.ActivityConfigTasker


class TogglingConditionHelper(config: TaskerPluginConfig<TogglingConditionInput>) : TaskerPluginConfigHelper<TogglingConditionInput, TogglingConditionOutput, TogglingConditionRunner>(config) {
    override val runnerClass = TogglingConditionRunner::class.java
    override val inputClass = TogglingConditionInput::class.java
    override val outputClass = TogglingConditionOutput::class.java
}

class ActivityConfigTogglingCondition : ActivityConfigTasker<TogglingConditionInput, TogglingConditionOutput, TogglingConditionRunner, TogglingConditionHelper>() {

    override fun assignFromInput(input: TaskerInput<TogglingConditionInput>) {
    }

    override fun getNewHelper(config: TaskerPluginConfig<TogglingConditionInput>) = TogglingConditionHelper(config)
    override val inputForTasker = TaskerInput(TogglingConditionInput())
    override val layoutResId = R.layout.activity_config_toggling_condition

}