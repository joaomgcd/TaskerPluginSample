package com.joaomgcd.taskerpluginsample.tasker.getartists

import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputObject
import com.joaomgcd.taskerpluginlibrary.output.TaskerOutputVariable
import com.joaomgcd.taskerpluginsample.R

/**
 * Example of an Output that contains nested arrays of data.
 * The output will be a list of artist names, album names and a list of songs where each position in the array will be all the artist's songs joined by a comma
 */

@TaskerOutputObject()
class MusicArtist(
    @get:TaskerOutputVariable("artist", labelResIdName = "artist_label", htmlLabelResIdName = "artist_html_label") val name: String,
    val album: MusicAlbum,
    val songs: Array<MusicSong>

)

@TaskerOutputObject()
class MusicSong(
    @get:TaskerOutputVariable("song", labelResIdName = "song_label", htmlLabelResIdName = "song_html_label") val name: String,
    @get:TaskerOutputVariable("duration", labelResIdName = "duration_label", htmlLabelResIdName = "duration_html_label") val duration: Int
)

@TaskerOutputObject()
class MusicAlbum(
    @get:TaskerOutputVariable("album", labelResIdName = "album_label", htmlLabelResIdName = "album_html_label") val name: String
)