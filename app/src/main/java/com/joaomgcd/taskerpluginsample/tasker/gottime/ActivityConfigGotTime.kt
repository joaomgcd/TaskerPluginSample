package com.joaomgcd.taskerpluginsample.tasker.gottime

import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelper
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginsample.R
import com.joaomgcd.taskerpluginsample.tasker.ActivityConfigTasker


class GotTimeHelper(config: TaskerPluginConfig<GotTimeInput>) : TaskerPluginConfigHelper<GotTimeInput, GotTimeOutput, GotTimeActionRunner>(config) {
    override val runnerClass = GotTimeActionRunner::class.java
    override val inputClass = GotTimeInput::class.java
    override val outputClass = GotTimeOutput::class.java
}

class ActivityConfigGotTime : ActivityConfigTasker<GotTimeInput, GotTimeOutput, GotTimeActionRunner, GotTimeHelper>() {
    override fun assignFromInput(input: TaskerInput<GotTimeInput>) {
    }

    override fun getNewHelper(config: TaskerPluginConfig<GotTimeInput>) = GotTimeHelper(config)
    override val inputForTasker = TaskerInput(GotTimeInput())
    override val layoutResId = R.layout.activity_config_gottime
}