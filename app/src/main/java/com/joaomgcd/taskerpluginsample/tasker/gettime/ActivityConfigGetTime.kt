package com.joaomgcd.taskerpluginsample.tasker.gettime

import android.os.Bundle
import android.view.LayoutInflater
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelper
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputInfo
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputInfos
import com.joaomgcd.taskerpluginsample.R
import com.joaomgcd.taskerpluginsample.databinding.ActivityConfigGettimeBinding
import com.joaomgcd.taskerpluginsample.selectOne
import com.joaomgcd.taskerpluginsample.tasker.ActivityConfigTasker
import com.joaomgcd.taskerpluginsample.toToast

import java.util.*

/**
 * Example of adding dynamic input.
 */


class GetTimeHelper(config: TaskerPluginConfig<GetTimeInput>) : TaskerPluginConfigHelper<GetTimeInput, GetTimeOutput, GetTimeRunner>(config) {
    override val runnerClass = GetTimeRunner::class.java
    override val inputClass = GetTimeInput::class.java
    override val outputClass = GetTimeOutput::class.java
    override fun isInputValid(input: TaskerInput<GetTimeInput>) = input.regular.formattedNowResult
    override val defaultInput = GetTimeInput("HH:mm")

    //The timecreated input is dynamically created so that it can be used in the output later. This input isn't related to the UI in any way, but it could be if you wanted
    override fun addInputs(input: TaskerInputInfos) {
        super.addInputs(input)
        input.add(TaskerInputInfo(KEY_TIME_CREATED, config.context.getString(R.string.time_created_epoch), null, false, Date().time))
    }
}

class ActivityConfigGetTime : ActivityConfigTasker<GetTimeInput, GetTimeOutput, GetTimeRunner, GetTimeHelper,ActivityConfigGettimeBinding>() {
    //Overrides
    override fun getNewHelper(config: TaskerPluginConfig<GetTimeInput>) = GetTimeHelper(config)

    override fun assignFromInput(input: TaskerInput<GetTimeInput>) = input.regular.run {
        binding?.editTextFormat?.setText(format)
        times?.let { binding?.editTextTimes?.setText(it.toString()) }
        binding?.editTextVariable?.setText(variableName)
        binding?.checkBoxGetSeconds?.isChecked = input.regular.getSeconds
    }

    override val inputForTasker get() = TaskerInput(GetTimeInput(binding?.editTextFormat?.text?.toString(), binding?.editTextTimes?.text?.toString()?.toIntOrNull(), binding?.editTextVariable?.text?.toString(), binding?.checkBoxGetSeconds?.isChecked?:false))
    override fun inflateBinding(layoutInflater: LayoutInflater) = ActivityConfigGettimeBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding?.editTextTimes?.setOnClickListener { showVariableDialog() }
    }

    private fun showVariableDialog() {
        val relevantVariables = taskerHelper.relevantVariables.toList()
        if (relevantVariables.isEmpty()) return "No variables to select.\n\nCreate some local variables in Tasker to show here.".toToast(this)

        selectOne("Select a Tasker variable", relevantVariables) { binding?.editTextTimes?.setText(it) }

    }
}