package com.joaomgcd.taskerpluginlibrary.action

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.joaomgcd.taskerpluginlibrary.R
import com.joaomgcd.taskerpluginlibrary.extensions.runFromTasker
import com.joaomgcd.taskerpluginlibrary.runner.ArgsSignalFinish
import com.joaomgcd.taskerpluginlibrary.runner.IntentServiceParallel
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultError
import net.dinglisch.android.tasker.TaskerPlugin


class BroadcastReceiverAction : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        resultCode = TaskerPlugin.Setting.RESULT_CODE_PENDING
        try {
            runFromTasker<IntentServiceAction>(context, intent)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}

class IntentServiceAction : IntentServiceParallel("IntentServiceTaskerAction") {
    private var taskerIntent: Intent? = null

    override fun onHandleIntent(intent: Intent) {
        startForegroundIfNeeded()
        taskerIntent = intent
        val result = TaskerPluginRunnerAction.runFromIntent(this, intent)
        if (!result.hasStartedForeground) {
            startForegroundIfNeeded()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action.equals(ACTION_STOP)) {
            stopSelf()
            taskerIntent?.let {
                TaskerPluginResultError(
                        InterruptedException(getString(R.string.cancelled))
                ).signalFinish(ArgsSignalFinish(this, it))
            }
            return START_NOT_STICKY
        }
        return super.onStartCommand(intent, flags, startId)
    }

    companion object {
        const val ACTION_STOP = "ACTION_STOP"
    }
}