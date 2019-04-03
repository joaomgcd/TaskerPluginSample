package com.joaomgcd.taskerpluginlibrary.runner

import android.annotation.TargetApi
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Build
import com.joaomgcd.taskerpluginlibrary.R
import com.joaomgcd.taskerpluginlibrary.extensions.hasToRunServicesInForeground
import com.joaomgcd.taskerpluginlibrary.extensions.inputClass
import com.joaomgcd.taskerpluginlibrary.extensions.runnerClass
import com.joaomgcd.taskerpluginlibrary.extensions.taskerPluginExtraBundle
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.output.TaskerOuputBase


abstract class TaskerPluginRunner<TInput : Any, TOutput : Any> {
    /**
     * Gets the input class for the runner from the extras bundle
     */
    fun getInputClass(taskerIntent: Intent): Class<TInput> = Class.forName(taskerIntent.taskerPluginExtraBundle.inputClass) as Class<TInput>

    /**
     * Notification Properties used to show the foreground notification on Android O or above
     */
    class NotificationProperties @JvmOverloads constructor(val notificationChannelNameResId: Int = R.string.tasker_plugin_service,
                                                           val notificationChannelDescriptionResId: Int = R.string.tasker_plugin_service_description,
                                                           val titleResId: Int = R.string.app_name,
                                                           val textResId: Int = R.string.running_tasker_plugin,
                                                           val iconResId: Int = R.mipmap.ic_launcher) {
        @TargetApi(Build.VERSION_CODES.O)
        fun getNotification(context: Context) = Notification.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(context.getString(titleResId))
                .setContentText(context.getString(textResId))
                .setSmallIcon(Icon.createWithResource(context, iconResId))
                .build()
    }

    /**
     * Can be overriden so that plugins can present customized foreground notifications when they are executing on Android O or above
     */
    protected open val notificationProperties get() = NotificationProperties()

    @TargetApi(Build.VERSION_CODES.O)
    protected fun IntentService.startForegroundIfNeeded() {
        TaskerPluginRunner.startForegroundIfNeeded(this, notificationProperties)
    }


    companion object {
        const val NOTIFICATION_CHANNEL_ID = "taskerpluginforegroundd"
        @TargetApi(Build.VERSION_CODES.O)
        fun Service.createNotificationChannel(channelId: String, notificationProperties: NotificationProperties) {
            val notificationManager = getSystemService(NotificationManager::class.java)
            val channel = NotificationChannel(channelId, getString(notificationProperties.notificationChannelNameResId), NotificationManager.IMPORTANCE_NONE)
            channel.description = getString(notificationProperties.notificationChannelDescriptionResId)
            notificationManager.createNotificationChannel(channel)
        }

        /**
         * Will start an IntentService in the foreground so that the app doesn't crash if it takes more than 5 seconds to execute
         */
        @TargetApi(Build.VERSION_CODES.O)
        fun startForegroundIfNeeded(intentService: Service, notificationProperties: NotificationProperties = NotificationProperties()) {
            if (!intentService.hasToRunServicesInForeground) return
            val channelId = NOTIFICATION_CHANNEL_ID
            intentService.createNotificationChannel(channelId, notificationProperties)
            val notification: Notification = notificationProperties.getNotification(intentService)
            intentService.startForeground(this.hashCode(), notification)
        }

        /**
         * Gets the plugin runner from the extras bundle
         */
        internal inline fun <reified TRunner : TaskerPluginRunner<*, *>> getFromTaskerIntent(taskerIntent: Intent?): TRunner? {
            val runnerClass = taskerIntent?.taskerPluginExtraBundle?.runnerClass
                    ?: return null

            val clazz = try {
                Class.forName(runnerClass)
            } catch (t: Throwable) {
                t.printStackTrace()
                null
            } ?: return null

            return try {
                clazz.newInstance() as TRunner
            } catch (t: Throwable) {
                t.printStackTrace()
                null
            }
        }

    }

    internal fun getRenames(context: Context, input: TaskerInput<TInput>?) = input?.let { TaskerOutputRenames().apply { addOutputVariableRenames(context, input, this) } }

    /**
     * Allows plugin developer to rename output variables based on user input. This allows user to choose his/her own names for output variables. Check GetTimeRunner example in Tasker Plugin Sample
     */
    open fun addOutputVariableRenames(context: Context, input: TaskerInput<TInput>, renames: TaskerOutputRenames) {}

    /**
     * Allows plugin developer to not output certain values. Useful because sometimes actions can return too many outputs, so this allows you to trim it down to just the needed
     */
    open fun shouldAddOutput(context: Context, input: TaskerInput<TInput>?, ouput: TaskerOuputBase) = true

}