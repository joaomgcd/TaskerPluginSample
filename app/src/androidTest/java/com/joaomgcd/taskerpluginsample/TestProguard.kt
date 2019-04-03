package com.joaomgcd.taskerpluginsample

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.joaomgcd.taskerpluginlibrary.action.TaskerPluginRunnerAction
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelper
import com.joaomgcd.taskerpluginlibrary.input.*
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputObject
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputVariable
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputsForConfig
import com.joaomgcd.taskerpluginlibrary.output.runner.TaskerOutputForRunner
import com.joaomgcd.taskerpluginlibrary.output.runner.TaskerOutputsForRunner
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResult
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultSucess
import com.joaomgcd.taskerpluginsample.tasker.ActivityConfigTasker
import com.joaomgcd.taskerpluginsample.tasker.getartists.MusicAlbum
import com.joaomgcd.taskerpluginsample.tasker.getartists.MusicArtist
import com.joaomgcd.taskerpluginsample.tasker.getartists.MusicSong
import com.joaomgcd.taskerpluginsample.tasker.getip.GetIPInput
import com.joaomgcd.taskerpluginsample.tasker.getip.GetIPInputOptions
import com.joaomgcd.taskerpluginsample.tasker.getip.GetIPOutput
import kotlinx.android.synthetic.main.activity_config_get_ip.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

@TaskerInputRoot
class TestInput @JvmOverloads constructor(
        @field:TaskerInputField("separator", R.string.separator) var separator: String? = null,
        var options: TestInputOptions = TestInputOptions()
)
@TaskerInputObject("options", R.string.options)
class TestInputOptions @JvmOverloads constructor(
        @field:TaskerInputField("split", R.string.split) var split: Boolean = false
)

@TaskerOutputObject
class TestOutput(
        @get:TaskerOutputVariable(GetIPOutput.VAR_IP, R.string.ip, R.string.ip_description) var publicIp: String?
)

class TestHelper(config: TaskerPluginConfig<TestInput>) : TaskerPluginConfigHelper<TestInput, TestOutput, TestRunner>(config) {
    override val runnerClass = TestRunner::class.java
    override val inputClass = TestInput::class.java
    override val outputClass = TestOutput::class.java

}

class ActivityConfigTest : ActivityConfigTasker<TestInput, TestOutput, TestRunner, TestHelper>() {
    //Overrides
    override fun getNewHelper(config: TaskerPluginConfig<TestInput>) = TestHelper(config)

    override fun assignFromInput(input: TaskerInput<TestInput>) = input.regular.run {
        editTextSeparator.setText(separator)
        checkboxSplit.isChecked = options.split
    }

    override val inputForTasker get() = TaskerInput(TestInput(editTextSeparator.text?.toString(), TestInputOptions(checkboxSplit.isChecked)))
    override val layoutResId = R.layout.activity_config_get_ip


}

class TestRunner : TaskerPluginRunnerAction<TestInput, TestOutput>() {

    override val notificationProperties = NotificationProperties(iconResId = R.drawable.plugin)
    override fun run(context: Context, input: TaskerInput<TestInput>): TaskerPluginResult<TestOutput> {
        var publicIp = with(URL("https://api.ipify.org").openConnection() as HttpURLConnection) {
            BufferedReader(InputStreamReader(inputStream)).use { it.readLine() }
        }

        val dynamic = if (input.regular.options.split) {
            TaskerOutputsForRunner().apply {
                add(TaskerOutputForRunner(GetIPOutput.VAR_SPLIT_IP, publicIp.split(".")))
            }
        } else {
            null
        }
        input.regular.separator?.let { separator -> publicIp = publicIp.replace(".", separator) }
        return TaskerPluginResultSucess(TestOutput(publicIp), dynamic)
    }

}

@RunWith(AndroidJUnit4::class)
class TestProguard {
    val context = InstrumentationRegistry.getTargetContext()


    val ipInput = GetIPInput(",,,", GetIPInputOptions(true))


    @Test
    fun neededClasses() {
        val inputClass = TestInput::class.java
        val outputClass = TestOutput::class.java
        val activityClass = ActivityConfigTest::class.java
        val runnerClass = TestRunner::class.java

        //class names
        assertEquals("com.joaomgcd.taskerpluginsample.TestInput", inputClass.name)
        assertEquals("com.joaomgcd.taskerpluginsample.TestOutput", outputClass.name)
        assertEquals("com.joaomgcd.taskerpluginsample.ActivityConfigTest", activityClass.name)
        assertEquals("com.joaomgcd.taskerpluginsample.TestRunner", runnerClass.name)


        //input annotations
        assertTrue(inputClass.isAnnotationPresent(TaskerInputRoot::class.java))
        assertTrue(TestInputOptions::class.java.isAnnotationPresent(TaskerInputObject::class.java))
        val testInputFields = inputClass.declaredFields
        assertEquals(2, testInputFields.size)
        val separatorField = testInputFields.first { it.type == String::class.java}
        assertTrue(separatorField.isAnnotationPresent(TaskerInputField::class.java))

        //output annotations
        assertTrue(outputClass.isAnnotationPresent(TaskerOutputObject::class.java))
        val testInputMethods = outputClass.declaredMethods
        assertEquals(2, testInputMethods.size)
        val publicIpMethod = testInputMethods.first { it.returnType != null }
        assertTrue(publicIpMethod.isAnnotationPresent(TaskerOutputVariable::class.java))


        //outputs
        val outputs = TaskerOutputsForConfig().apply { add(context, outputClass) }
        assertEquals(1,outputs.size)
        val outputsMultiple = TaskerOutputsForConfig().apply { add(context, arrayOf(TestOutput("asds"))::class.java) }
        assertEquals(1,outputsMultiple.size)
        val outputsMusicArtist = TaskerOutputsForConfig().apply { add(context, MusicArtist::class.java) }
        assertEquals(4, outputsMusicArtist.size)
        val musicArtistMultiple = arrayOf(
                MusicArtist("Pearl Jam", MusicAlbum("Backspacer"), arrayOf(MusicSong("Even Flow", 200), MusicSong("Alive", 300), MusicSong("Red Mosquito", 100))),
                MusicArtist("Rui Veloso", MusicAlbum("Mingos E Os Samurais"), arrayOf(MusicSong("Sayago Blues", 360), MusicSong("Dia De Passeio", 240))),
                MusicArtist("Sublime", MusicAlbum("Sublime"), arrayOf(MusicSong("Pawn Shop", 1)))
        )
        val outputsForConfigMultipleNested = TaskerOutputsForConfig().apply { add(context, musicArtistMultiple::class.java) }
        assertEquals(4, outputsForConfigMultipleNested.size)
    }

}
