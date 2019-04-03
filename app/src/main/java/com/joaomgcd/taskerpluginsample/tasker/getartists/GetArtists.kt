package com.joaomgcd.taskerpluginsample.tasker.getartists

import android.content.Context
import com.joaomgcd.taskerpluginlibrary.action.TaskerPluginRunnerActionNoInput
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelperNoInput
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResult
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultSucess
import com.joaomgcd.taskerpluginsample.tasker.ActivityConfigTaskerNoInput

/**
 * Example of an Action that outputs nested arrays of data.
 * The output will contain a list of artist names, album names and a list of songs where each position in the array will be all the artist's songs joined by a comma
 */

class GetArtistsRunner : TaskerPluginRunnerActionNoInput<Array<MusicArtist>>() {
    override fun run(context: Context, input: TaskerInput<Unit>): TaskerPluginResult<Array<MusicArtist>> {
        val result = arrayOf(
                MusicArtist("Pearl Jam", MusicAlbum("Backspacer"), arrayOf(MusicSong("Even Flow", 200), MusicSong("Alive", 300))),
                MusicArtist("Rui Veloso", MusicAlbum("Mingos E Os Samurais"), arrayOf(MusicSong("Sayago Blues", 360), MusicSong("Dia De Passeio", 240)))
        )
        return TaskerPluginResultSucess(result)
    }
}

class GetArtistsHelper(config: TaskerPluginConfig<Unit>) : TaskerPluginConfigHelperNoInput<Array<MusicArtist>, GetArtistsRunner>(config) {
    override val runnerClass = GetArtistsRunner::class.java
    override fun addToStringBlurb(input: TaskerInput<Unit>, blurbBuilder: StringBuilder) {
        blurbBuilder.append("This will return info about multiple artists")
    }

    //outputClass has to be set to Array if you want Tasker variables to be generated correctly
    override val outputClass = Array<MusicArtist>::class.java
}

class GetArtistsActivity : ActivityConfigTaskerNoInput<Array<MusicArtist>, GetArtistsRunner, GetArtistsHelper>() {
    override fun getNewHelper(config: TaskerPluginConfig<Unit>) = GetArtistsHelper(config)

}
