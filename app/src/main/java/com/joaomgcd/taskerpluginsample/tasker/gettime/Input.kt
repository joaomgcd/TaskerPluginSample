package com.joaomgcd.taskerpluginsample.tasker.gettime

import com.joaomgcd.taskerpluginlibrary.SimpleResult
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputField
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputRoot
import com.joaomgcd.taskerpluginsample.R
import java.text.SimpleDateFormat
import java.util.*


const val KEY_TIME_CREATED = "timecreated"

@TaskerInputRoot
class GetTimeInput @JvmOverloads constructor(
        @field:TaskerInputField("format", labelResIdName = "format") var format: String? = null,
        @field:TaskerInputField("timesInt",labelResIdName = "times") var times: Int? = null,
        @field:TaskerInputField("variableName", labelResIdName = "variable", ignoreInStringBlurb = true) var variableName: String? = null,
        @field:TaskerInputField("getSeconds", labelResIdName = "get_seconds") var getSeconds: Boolean = true
) {
    //Your Inputs are normal classes and thus can have their own functions and properties
    fun format(date: Date) = SimpleDateFormat(format).format(date)
    fun format(time: Long) = format(Date(time))
    val formattedNowResult = SimpleResult.get { format(Date()) }
}