package com.joaomgcd.taskerpluginsample.tasker.outputorerror

import android.content.Context
import com.joaomgcd.taskerpluginlibrary.action.TaskerPluginRunnerActionNoInput
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelperNoInput
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputObject
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputVariable
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResult
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultErrorWithOutput
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultSucess
import com.joaomgcd.taskerpluginsample.tasker.ActivityConfigTaskerNoInput
import java.util.*


@TaskerOutputObject()
class OutputOrErrorOutput(
    @get:TaskerOutputVariable("output_value") var output: String?
)


private var isError = false

class OutputOrErrorRunner : TaskerPluginRunnerActionNoInput<OutputOrErrorOutput>() {
    override fun run(context: Context, input: TaskerInput<Unit>): TaskerPluginResult<OutputOrErrorOutput> {
        val result = if (isError) {
            TaskerPluginResultErrorWithOutput(1, "This action ended in error. Check the %err and %errmsg variables.")
        } else {
            TaskerPluginResultSucess(OutputOrErrorOutput("Action ran successfully!"))
        }
        isError = !isError
        return result
    }
}

class OutputOrErrorHelper(config: TaskerPluginConfig<Unit>) : TaskerPluginConfigHelperNoInput<OutputOrErrorOutput, OutputOrErrorRunner>(config) {
    override val runnerClass = OutputOrErrorRunner::class.java
    override fun addToStringBlurb(input: TaskerInput<Unit>, blurbBuilder: StringBuilder) {
        blurbBuilder.append("This will either output an error (check the %err and %errmsg variables) or output a value in the %output variable")
    }

    override val outputClass = OutputOrErrorOutput::class.java
}

class OutputOrErrorActivity : ActivityConfigTaskerNoInput<OutputOrErrorOutput, OutputOrErrorRunner, OutputOrErrorHelper>() {
    override fun getNewHelper(config: TaskerPluginConfig<Unit>) = OutputOrErrorHelper(config)

}
