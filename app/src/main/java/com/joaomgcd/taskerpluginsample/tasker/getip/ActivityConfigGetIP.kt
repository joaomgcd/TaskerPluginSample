package com.joaomgcd.taskerpluginsample.tasker.getip

import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelper
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputForConfig
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputsForConfig
import com.joaomgcd.taskerpluginsample.R
import com.joaomgcd.taskerpluginsample.tasker.ActivityConfigTasker
import kotlinx.android.synthetic.main.activity_config_get_ip.*


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

class ActivityConfigGetIP : ActivityConfigTasker<GetIPInput, GetIPOutput, GetIPRunner, GetIPHelper>() {
    //Overrides
    override fun getNewHelper(config: TaskerPluginConfig<GetIPInput>) = GetIPHelper(config)

    override fun assignFromInput(input: TaskerInput<GetIPInput>) = input.regular.run {
        editTextSeparator.setText(separator)
        checkboxSplit.isChecked = options.split
    }

    override val inputForTasker get() = TaskerInput(GetIPInput(editTextSeparator.text?.toString(), GetIPInputOptions(checkboxSplit.isChecked)))
    override val layoutResId = R.layout.activity_config_get_ip


}