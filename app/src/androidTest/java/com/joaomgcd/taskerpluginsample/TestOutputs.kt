package com.joaomgcd.taskerpluginsample

import android.os.Bundle
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputVariable
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputsForConfig
import com.joaomgcd.taskerpluginlibrary.output.runner.TaskerOutputForRunner
import com.joaomgcd.taskerpluginlibrary.output.runner.TaskerOutputsForRunner
import com.joaomgcd.taskerpluginsample.tasker.getartists.MusicAlbum
import com.joaomgcd.taskerpluginsample.tasker.getartists.MusicArtist
import com.joaomgcd.taskerpluginsample.tasker.getartists.MusicSong
import com.joaomgcd.taskerpluginsample.tasker.getip.GetIPOutput
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

class MusicState(
        @get:TaskerOutputVariable(R.string.playing_variable, R.string.playing_label, R.string.playing_html_label) val playing: Boolean,
        @get:TaskerOutputVariable(R.string.artist, R.string.artist_label, R.string.artist_html_label) val artist: String,
        @get:TaskerOutputVariable(R.string.song, R.string.song_label, R.string.song_html_label) val songName: String
)


@RunWith(AndroidJUnit4::class)
class TestOutputs {
    val context = InstrumentationRegistry.getTargetContext()


    val musicArtistMultiple = arrayOf(
            MusicArtist("Pearl Jam", MusicAlbum("Backspacer"), arrayOf(MusicSong("Even Flow", 200), MusicSong("Alive", 300), MusicSong("Red Mosquito", 100))),
            MusicArtist("Rui Veloso", MusicAlbum("Mingos E Os Samurais"), arrayOf(MusicSong("Sayago Blues", 360), MusicSong("Dia De Passeio", 240))),
            MusicArtist("Sublime", MusicAlbum("Sublime"), arrayOf(MusicSong("Pawn Shop", 1)))
    )
    val outputsForConfigMultipleNested = TaskerOutputsForConfig().apply { add(context, musicArtistMultiple::class.java) }
    val outputsForRunnerMultipleNested = TaskerOutputsForRunner().apply { add(context, musicArtistMultiple::class.java, musicArtistMultiple) }

    val musicStateMultiple = arrayOf(MusicState(true, "Pearl Jam", "Even Flow"), MusicState(true, "Alice In Chains", "Bones"))
    val outputsForConfigMultiple = TaskerOutputsForConfig().apply { add(context, musicStateMultiple::class.java) }
    val outputsForRunnerMultiple = TaskerOutputsForRunner().apply { add(context, musicStateMultiple::class.java, musicStateMultiple) }

    val musicStateSingle = MusicState(true, "Pearl Jam", "Even Flow")
    val outputsForConfigSingle = TaskerOutputsForConfig().apply { add(context, musicStateSingle::class.java) }
    val outputsForRunnerSingle = TaskerOutputsForRunner().apply { add(context, musicStateSingle::class.java, musicStateSingle) }


    @Test
    fun pluginOutputSizeSingle() {

        assertEquals(outputsForConfigSingle.size, outputsForRunnerSingle.size)
        assertEquals(3, outputsForRunnerSingle.size)

        outputsForConfigSingle.forEach { assertFalse(it.isMultiple) }
    }

    @Test
    fun pluginOutputValuesSingle() {
        val bundle = Bundle()
        assertEquals("artist", outputsForConfigSingle.first { it.nameNoSuffix == "artist" }.name)
        outputsForRunnerSingle.forEach { it.addToBundle(bundle) }
        assertEquals("Pearl Jam", bundle.getString("%artist"))
    }

    @Test
    fun pluginOutputSizeMultiple() {

        assertEquals(3, outputsForConfigMultiple.size)
        assertEquals(6, outputsForRunnerMultiple.size)

        outputsForConfigMultiple.forEach { assertTrue(it.isMultiple) }

    }

    @Test
    fun pluginOutputValuesMultiple() {
        val bundle = Bundle()
        assertEquals("artist()", outputsForConfigMultiple.first { it.nameNoSuffix == "artist" }.name)
        outputsForRunnerMultiple.forEach { it.addToBundle(bundle) }
        assertEquals("Alice In Chains", bundle.getString("%artist2"))
    }

    @Test
    fun pluginOutputValuesMultipleNested() {
        var bundle = Bundle()
        assertEquals(4, outputsForConfigMultipleNested.size)
        assertEquals("artist()", outputsForConfigMultipleNested.first { it.nameNoSuffix == "artist" }.name)
        assertEquals("song()", outputsForConfigMultipleNested.first { it.nameNoSuffix == "song" }.name)
        assertEquals("album()", outputsForConfigMultipleNested.first { it.nameNoSuffix == "album" }.name)
        assertEquals("duration()", outputsForConfigMultipleNested.first { it.nameNoSuffix == "duration" }.name)
        bundle = TaskerOutputsForRunner.getVariableBundle(context, musicArtistMultiple)
        assertEquals(null, bundle.getString("%artist"))
        assertEquals("Pearl Jam", bundle.getString("%artist1"))
        assertEquals("Rui Veloso", bundle.getString("%artist2"))
        assertEquals("Sublime", bundle.getString("%artist3"))
        assertEquals("Even Flow,Alive,Red Mosquito", bundle.getString("%song1"))
        assertEquals("Pawn Shop", bundle.getString("%song3"))
    }

    @Test
    fun pluginOutputIp() {
        val publicIp = "1.2.4.5"
        val dynamic =
                TaskerOutputsForRunner().apply {
                    add(TaskerOutputForRunner(GetIPOutput.VAR_SPLIT_IP, publicIp.split(".")))
                }

        var bundle = TaskerOutputsForRunner.getVariableBundle(context, GetIPOutput(publicIp), dynamic)
        assertEquals(publicIp, bundle.getString("%aip"))
        assertEquals("1", bundle.getString("%splitip1"))
        assertEquals("2", bundle.getString("%splitip2"))
        assertEquals("4", bundle.getString("%splitip3"))
        assertEquals("5", bundle.getString("%splitip4"))
    }
}
