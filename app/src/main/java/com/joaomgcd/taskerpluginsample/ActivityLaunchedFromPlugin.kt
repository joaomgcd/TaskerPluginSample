package com.joaomgcd.taskerpluginsample

import android.app.Activity
import android.os.Bundle


class ActivityLaunchedFromPlugin : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launched_from_background)
    }

}