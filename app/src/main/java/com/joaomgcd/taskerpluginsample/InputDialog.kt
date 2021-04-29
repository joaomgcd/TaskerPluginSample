package com.joaomgcd.taskerpluginsample

import android.app.Activity
import android.app.AlertDialog
import android.widget.EditText
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class InputDialog(val activity: Activity,val title:String) {
    suspend fun show() = suspendCoroutine<String> { continuation ->
        val builder = AlertDialog.Builder(activity).apply {
            setTitle(title)
            val input = EditText(activity)
            setView(input)
            setPositiveButton("OK") { _, _ -> continuation.resume(input.text.toString()) }
            setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
                continuation.resume("")
            }
        }

        builder.show()
    }
}