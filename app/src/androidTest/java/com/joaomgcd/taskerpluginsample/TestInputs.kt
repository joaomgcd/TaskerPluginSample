package com.joaomgcd.taskerpluginsample

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.joaomgcd.taskerpluginlibrary.input.TaskerInputInfos
import com.joaomgcd.taskerpluginsample.tasker.getip.GetIPInput
import com.joaomgcd.taskerpluginsample.tasker.getip.GetIPInputOptions
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TestInputs {
    val context = InstrumentationRegistry.getTargetContext()


    val ipInput = GetIPInput(",,,", GetIPInputOptions(true))


    @Test
    fun getInput() {
        assertEquals("com.joaomgcd.taskerpluginsample.tasker.getip.GetIPInput",GetIPInput::class.java.name)
        val inputInfos = TaskerInputInfos.fromInput(context, ipInput)
        assertEquals(2, inputInfos.size)
        assertEquals("separator", inputInfos[0].key)
        assertEquals("options.optionsip.split", inputInfos[1].key)
        assertNotNull(inputInfos)

        val newInput = GetIPInput()
        TaskerInputInfos.fromBundle(context, newInput, inputInfos.bundle)
        assertEquals(",,,",newInput.separator)
        assertEquals(true,newInput.options.split )
    }

}
