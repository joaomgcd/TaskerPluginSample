package com.joaomgcd.taskerpluginsample.tasker.getip

import android.content.Context
import com.joaomgcd.taskerpluginlibrary.action.TaskerPluginRunnerAction
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.output.runner.TaskerOutputForRunner
import com.joaomgcd.taskerpluginlibrary.output.runner.TaskerOutputsForRunner
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResult
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultSucess
import com.joaomgcd.taskerpluginsample.R
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class GetIPRunner : TaskerPluginRunnerAction<GetIPInput, GetIPOutput>() {

    //A custom notification icon is set for the foreground notification the action will have if the app targets API 26 or above
    override val notificationProperties get() = NotificationProperties(iconResId = R.drawable.plugin)
    override fun run(context: Context, input: TaskerInput<GetIPInput>): TaskerPluginResult<GetIPOutput> {
        var publicIp = with(URL("https://api.ipify.org").openConnection() as HttpURLConnection) {
            BufferedReader(InputStreamReader(inputStream)).use { it.readLine() }
        }

        //If the split option is enabled an extra output is created with the splitip key
        val dynamic = if (input.regular.options.split) {
            TaskerOutputsForRunner().apply {
                add(TaskerOutputForRunner(GetIPOutput.VAR_SPLIT_IP, publicIp.split(".")))
            }
        } else {
            null
        }
        input.regular.separator?.let { separator -> publicIp = publicIp.replace(".", separator) }
        return TaskerPluginResultSucess(GetIPOutput(publicIp), dynamic)
    }

}