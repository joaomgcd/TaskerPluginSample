package com.joaomgcd.taskerpluginlibrary.action

import android.app.IntentService
import android.content.Context
import android.content.Intent
import com.joaomgcd.taskerpluginlibrary.extensions.getTaskerInput
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.output.runner.TaskerOutputForRunner
import com.joaomgcd.taskerpluginlibrary.runner.*


abstract class TaskerPluginRunnerAction<TInput : Any, TOutput : Any>() : TaskerPluginRunner<TInput, TOutput>() {

    internal fun runWithIntent(context: IntentServiceParallel?, taskerIntent: Intent?) :RunnerActionResult{
        if (context == null) return RunnerActionResult(false)
        if (taskerIntent == null) return RunnerActionResult(false)
        context.startForegroundIfNeeded()
        try {
            val input = taskerIntent.getTaskerInput(context, getInputClass(taskerIntent))
            var result = run(context, input)
            result.signalFinish(getArgsSignalFinish(context, taskerIntent, input))
        } catch (t: Throwable) {
            TaskerPluginResultError(t).signalFinish(getArgsSignalFinish(context, taskerIntent))
        }
        return RunnerActionResult(true)
    }

    abstract fun run(context: Context, input: TaskerInput<TInput>): TaskerPluginResult<TOutput>

    companion object {
        internal fun runFromIntent(context: IntentServiceParallel?, taskerIntent: Intent?): RunnerActionResult {
            if (context == null) return RunnerActionResult(false)
            if (taskerIntent == null) return RunnerActionResult(false)
            val runner = TaskerPluginRunner.getFromTaskerIntent<TaskerPluginRunnerAction<*, *>>(taskerIntent)
            if (runner == null) {
                TaskerPluginResultError(0, "Couldn't get action runner from intent").signalFinish(ArgsSignalFinish(context, taskerIntent))
                return RunnerActionResult(false)
            }
           return runner.runWithIntent(context, taskerIntent)
        }
    }
    fun getArgsSignalFinish(context: Context, taskerIntent: Intent, input: TaskerInput<TInput>? = null) = ArgsSignalFinish(context, taskerIntent, getRenames(context, input), { output: TaskerOutputForRunner -> shouldAddOutput(context, input, output) })

    class RunnerActionResult(val hasStartedForeground: Boolean)
}

