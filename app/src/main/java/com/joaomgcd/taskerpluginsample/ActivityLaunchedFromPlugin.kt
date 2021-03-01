package com.joaomgcd.taskerpluginsample

import android.app.Activity
import android.os.Bundle
import android.widget.CompoundButton
import com.joaomgcd.taskerpluginlibrary.extensions.requestQuery
import com.joaomgcd.taskerpluginsample.tasker.playstatechanged.PlayState
import com.joaomgcd.taskerpluginsample.tasker.playstatechanged.PlayStateChangedActivity
import com.joaomgcd.taskerpluginsample.tasker.togglingcondition.TogglingConditionRunner
import kotlinx.android.synthetic.main.activity_main.*


class ActivityLaunchedFromPlugin : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launched_from_background)
    }

}