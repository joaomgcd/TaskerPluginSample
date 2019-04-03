package com.joaomgcd.taskerpluginsample.tasker.gettime

import android.os.Bundle
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelper
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputInfo
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputInfos
import com.joaomgcd.taskerpluginsample.R
import com.joaomgcd.taskerpluginsample.selectOne
import com.joaomgcd.taskerpluginsample.tasker.ActivityConfigTasker
import com.joaomgcd.taskerpluginsample.toToast
import kotlinx.android.synthetic.main.activity_config_gettime.*
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

class ActivityConfigGetTime : ActivityConfigTasker<GetTimeInput, GetTimeOutput, GetTimeRunner, GetTimeHelper>() {
    //Overrides
    override fun getNewHelper(config: TaskerPluginConfig<GetTimeInput>) = GetTimeHelper(config)

    override fun assignFromInput(input: TaskerInput<GetTimeInput>) = input.regular.run {
        editTextFormat.setText(format)
        times?.let { editTextTimes.setText(it.toString()) }
        editTextVariable.setText(variableName)
        checkBoxGetSeconds.isChecked = input.regular.getSeconds
    }

    override val inputForTasker get() = TaskerInput(GetTimeInput(editTextFormat.text?.toString(), editTextTimes.text?.toString()?.toIntOrNull(), editTextVariable.text?.toString(), checkBoxGetSeconds.isChecked))
    override val layoutResId = R.layout.activity_config_gettime

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editTextTimes.setOnClickListener { showVariableDialog() }
    }

    private fun showVariableDialog() {
        val relevantVariables = taskerHelper.relevantVariables.toList()
        if (relevantVariables.isEmpty()) return "No variables to select.\n\nCreate some local variables in Tasker to show here.".toToast(this)

        selectOne("Select a Tasker variable", relevantVariables) { editTextTimes.setText(it) }

    }
}