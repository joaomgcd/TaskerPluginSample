package com.joaomgcd.taskerpluginsample.tasker

import android.app.Activity
import android.os.Bundle
import android.view.KeyEvent
import com.joaomgcd.taskerpluginlibrary.SimpleResultError
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelper
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginRunner
import com.joaomgcd.taskerpluginsample.alert

/**
 * Base class for all the ConfigActivities in this example. This is totally optional. You can use any base class you want as long as it implements TaskerPluginConfig
 */
abstract class ActivityConfigTasker<TInput : Any, TOutput : Any, TActionRunner : TaskerPluginRunner<TInput, TOutput>, THelper : TaskerPluginConfigHelper<TInput, TOutput, TActionRunner>> : Activity(), TaskerPluginConfig<TInput> {
    abstract fun getNewHelper(config: TaskerPluginConfig<TInput>): THelper
    abstract val layoutResId: Int

    protected val taskerHelper by lazy { getNewHelper(this) }

    open val isConfigurable = true
    override val context get() = applicationContext
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isConfigurable) {
            taskerHelper.finishForTasker()
            return
        }
        setContentView(layoutResId)
        taskerHelper.onCreate()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount == 0) {
            val result = taskerHelper.onBackPressed()
            if (result is SimpleResultError) {
                alert("Warning", "Settings are not valid:\n\n${result.message}")
            }
            return result.success
        } else super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
    }
}


abstract class ActivityConfigTaskerNoOutput<TInput : Any, TActionRunner : TaskerPluginRunner<TInput, Unit>, THelper : TaskerPluginConfigHelper<TInput, Unit, TActionRunner>> : ActivityConfigTasker<TInput, Unit, TActionRunner, THelper>()
abstract class ActivityConfigTaskerNoInput<TOutput : Any, TActionRunner : TaskerPluginRunner<Unit, TOutput>, THelper : TaskerPluginConfigHelper<Unit, TOutput, TActionRunner>> : ActivityConfigTasker<Unit, TOutput, TActionRunner, THelper>() {
    override fun assignFromInput(input: TaskerInput<Unit>) {}
    override val inputForTasker = TaskerInput(Unit)
    override val layoutResId = 0
    override val isConfigurable = false
}

abstract class ActivityConfigTaskerNoOutputOrInput<TActionRunner : TaskerPluginRunner<Unit, Unit>, THelper : TaskerPluginConfigHelper<Unit, Unit, TActionRunner>> : ActivityConfigTaskerNoInput<Unit, TActionRunner, THelper>()