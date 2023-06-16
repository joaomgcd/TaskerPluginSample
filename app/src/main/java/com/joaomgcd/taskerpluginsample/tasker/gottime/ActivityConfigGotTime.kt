package com.joaomgcd.taskerpluginsample.tasker.gottime

import android.view.LayoutInflater
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelper
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginsample.R
import com.joaomgcd.taskerpluginsample.databinding.ActivityConfigGottimeBinding
import com.joaomgcd.taskerpluginsample.tasker.ActivityConfigTasker


class GotTimeHelper(config: TaskerPluginConfig<GotTimeInput>) : TaskerPluginConfigHelper<GotTimeInput, GotTimeOutput, GotTimeActionRunner>(config) {
    override val runnerClass = GotTimeActionRunner::class.java
    override val inputClass = GotTimeInput::class.java
    override val outputClass = GotTimeOutput::class.java
}

class ActivityConfigGotTime : ActivityConfigTasker<GotTimeInput, GotTimeOutput, GotTimeActionRunner, GotTimeHelper, ActivityConfigGottimeBinding>() {
    override fun assignFromInput(input: TaskerInput<GotTimeInput>) {
    }

    override fun getNewHelper(config: TaskerPluginConfig<GotTimeInput>) = GotTimeHelper(config)
    override val inputForTasker = TaskerInput(GotTimeInput())
    override fun inflateBinding(layoutInflater: LayoutInflater) = ActivityConfigGottimeBinding.inflate(layoutInflater)
}