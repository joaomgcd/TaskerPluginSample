package com.joaomgcd.taskerpluginsample.tasker.getip

import com.joaomgcd.taskerpluginlibrary.input.TaskerInputField
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputObject
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputRoot
import com.joaomgcd.taskerpluginsample.R

@TaskerInputRoot
class GetIPInput @JvmOverloads constructor(
        @field:TaskerInputField("separator", R.string.separator) var separator: String? = null,
        //Nested Input example. Notice how GetIPInputOptions also has the @TaskerInputObject annotation.
        @field:TaskerInputObject("optionsip") var options: GetIPInputOptions = GetIPInputOptions()
)

@TaskerInputObject("options", R.string.options)
class GetIPInputOptions @JvmOverloads constructor(
        @field:TaskerInputField("split", R.string.split) var split: Boolean = false
)