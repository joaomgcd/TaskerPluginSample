package com.joaomgcd.taskerpluginsample

import android.app.Activity
import android.os.Bundle
import android.widget.CompoundButton
import com.joaomgcd.taskerpluginlibrary.extensions.requestQuery
import com.joaomgcd.taskerpluginsample.tasker.playstatechanged.PlayState
import com.joaomgcd.taskerpluginsample.tasker.playstatechanged.PlayStateChangedActivity
import com.joaomgcd.taskerpluginsample.tasker.togglingcondition.TogglingConditionRunner
import kotlinx.android.synthetic.main.activity_main.*


class ActivityMain : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setToggleButtonText()
        buttonToggleCondition.setOnClickListener {
            TogglingConditionRunner.toggle(this)
            setToggleButtonText()
        }

        radioPlaying.setOnCheckedChangeListener { radio, _ -> changePlayingState(radio, true) }
        radioStopped.setOnCheckedChangeListener { radio, _ -> changePlayingState(radio, false) }
    }

    private fun setToggleButtonText() {
        buttonToggleCondition.text = "Set 'Toggling' condition to ${!TogglingConditionRunner.isOn}"
    }

    fun changePlayingState(radio: CompoundButton, isPlaying: Boolean) {
        if (!radio.isChecked) return
        val artist = (editTextArtist.text?.toString() ?: "Unkonwn")
        val songName = editTextSong.text?.toString() ?: "Unkonwn"
        val update = PlayState(isPlaying, artist, songName)
        PlayStateChangedActivity::class.java.requestQuery(this, update)
    }
}