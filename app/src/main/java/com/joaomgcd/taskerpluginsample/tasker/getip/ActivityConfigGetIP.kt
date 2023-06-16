package com.joaomgcd.taskerpluginsample.tasker.getip

import android.view.LayoutInflater
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelper
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputForConfig
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputsForConfig
import com.joaomgcd.taskerpluginsample.R
import com.joaomgcd.taskerpluginsample.databinding.ActivityConfigGetIpBinding
import com.joaomgcd.taskerpluginsample.tasker.ActivityConfigTasker


/**
 * Example of outputting dynamic data.
 */

class GetIPHelper(config: TaskerPluginConfig<GetIPInput>) : TaskerPluginConfigHelper<GetIPInput, GetIPOutput, GetIPRunner>(config) {
    override val runnerClass = GetIPRunner::class.java
    override val inputClass = GetIPInput::class.java
    override val outputClass = GetIPOutput::class.java

    //splitip output info is added dynamically depending on the split option in the input. Check the GetIpRunner to check how this is added as the output data
    override fun addOutputs(input: TaskerInput<GetIPInput>, output: TaskerOutputsForConfig) {
        super.addOutputs(input, output)
        if (input.regular.options.split) {
            output.add(TaskerOutputForConfig(GetIPOutput.VAR_SPLIT_IP, config.context.getString(R.string.splitip), config.context.getString(R.string.splitip_description), true))
        }
    }
}

class ActivityConfigGetIP : ActivityConfigTasker<GetIPInput, GetIPOutput, GetIPRunner, GetIPHelper, ActivityConfigGetIpBinding>() {
    //Overrides
    override fun getNewHelper(config: TaskerPluginConfig<GetIPInput>) = GetIPHelper(config)

    override fun assignFromInput(input: TaskerInput<GetIPInput>) = input.regular.run {
        binding?.editTextSeparator?.setText(separator)
        binding?.checkboxSplit?.isChecked = options.split
    }

    override val inputForTasker get() = TaskerInput(GetIPInput(binding?.editTextSeparator?.text?.toString(), GetIPInputOptions(binding?.checkboxSplit?.isChecked?:false)))
    override fun inflateBinding(layoutInflater: LayoutInflater) = ActivityConfigGetIpBinding.inflate(layoutInflater)

}