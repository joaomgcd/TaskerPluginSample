package com.joaomgcd.taskerpluginsample.tasker.basic

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelperConditionNoOutputOrInputOrUpdate
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelperEventNoOutputOrInputOrUpdate
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigNoInput
import com.joaomgcd.taskerpluginlibrary.extensions.requestQuery
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput

class BasicEventHelper(config: TaskerPluginConfig<Unit>) : TaskerPluginConfigHelperEventNoOutputOrInputOrUpdate(config) {
    override fun addToStringBlurb(input: TaskerInput<Unit>, blurbBuilder: StringBuilder) {
        blurbBuilder.append("Will trigger this app's Tasker event")
    }
}

class ActivityConfigBasicEvent : Activity(), TaskerPluginConfigNoInput {
    override val context get() = applicationContext
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BasicEventHelper(this).finishForTasker()
    }
}

fun Context.triggerBasicTaskerEvent() = ActivityConfigBasicEvent::class.java.requestQuery(this)