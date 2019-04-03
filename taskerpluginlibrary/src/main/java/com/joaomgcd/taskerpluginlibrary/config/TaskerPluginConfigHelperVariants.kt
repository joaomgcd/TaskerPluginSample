package com.joaomgcd.taskerpluginlibrary.config

import com.joaomgcd.taskerpluginlibrary.action.TaskerPluginRunnerActionNoInput
import com.joaomgcd.taskerpluginlibrary.action.TaskerPluginRunnerActionNoOutputOrInput
import com.joaomgcd.taskerpluginlibrary.action.TaskerPluginRunnerActionNoOutput


abstract class TaskerPluginConfigHelperNoOutput<TInput : Any, TActionRunner : TaskerPluginRunnerActionNoOutput<TInput>>(config: TaskerPluginConfig<TInput>) : TaskerPluginConfigHelper<TInput, Unit, TActionRunner>(config){
    override val outputClass = Unit::class.java
}
abstract class TaskerPluginConfigHelperNoInput<TOutput : Any, TActionRunner : TaskerPluginRunnerActionNoInput<TOutput>>(config: TaskerPluginConfig<Unit>) : TaskerPluginConfigHelper<Unit, TOutput, TActionRunner>(config){
    override val inputClass = Unit::class.java
}

abstract class TaskerPluginConfigHelperNoOutputOrInput<TActionRunner: TaskerPluginRunnerActionNoOutputOrInput>(config: TaskerPluginConfig<Unit>) : TaskerPluginConfigHelperNoOutput<Unit, TActionRunner>(config){
    override val inputClass = Unit::class.java
    override val outputClass = Unit::class.java
}