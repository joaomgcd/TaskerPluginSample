package com.joaomgcd.taskerpluginsample.tasker.gettime

import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputObject
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputVariable
import com.joaomgcd.taskerpluginsample.R
import java.text.SimpleDateFormat
import java.util.*


@TaskerOutputObject()
class GetTimeOutput(
        @get:TaskerOutputVariable(GetTimeOutput.VAR_FORMATTED_TIME, R.string.formatted_time, R.string.formatted_time_description) var formattedTime: String?,
        @get:TaskerOutputVariable("min_api_example", R.string.min_api_example, R.string.min_api_example_description, minApi = 27) var minApiExample: String,
        @get:TaskerOutputVariable("time", R.string.time, R.string.time_description) var time: Long = Date().time,
        //nested output. Notice how DateParts also has the @TaskerOutputObject annotation
        var dateParts: DateParts = DateParts(time)

){
    companion object {
        const val VAR_SECONDS = "seconds"
        const val VAR_FORMATTED_TIME = "formatted_time"
    }
}

@TaskerOutputObject()
class DateParts(time: Long) {
    private val date = Date(time)

    @get:TaskerOutputVariable("hours", R.string.hours, R.string.hours_description)
    val hours: String = SimpleDateFormat("HH").format(date)
    @get:TaskerOutputVariable("minutes", R.string.minutes, R.string.minutes_description)
    val minutes: String = SimpleDateFormat("mm").format(date)
    @get:TaskerOutputVariable(GetTimeOutput.VAR_SECONDS, R.string.seconds, R.string.seconds_description)
    val seconds: String = SimpleDateFormat("ss").format(date)
}