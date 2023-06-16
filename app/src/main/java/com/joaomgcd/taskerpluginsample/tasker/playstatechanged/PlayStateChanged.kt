package com.joaomgcd.taskerpluginsample.tasker.playstatechanged

import android.content.Context
import android.view.LayoutInflater
import com.joaomgcd.taskerpluginlibrary.condition.TaskerPluginRunnerConditionEvent
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfig
import com.joaomgcd.taskerpluginlibrary.config.TaskerPluginConfigHelper
import com.joaomgcd.taskerpluginlibrary.input.TaskerInput
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultCondition
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultConditionSatisfied
import com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginResultConditionUnsatisfied
import com.joaomgcd.taskerpluginsample.R
import com.joaomgcd.taskerpluginsample.checkedRadioButton
import com.joaomgcd.taskerpluginsample.databinding.ActivityConfigPlaystateChangedBinding
import com.joaomgcd.taskerpluginsample.tasker.ActivityConfigTasker


class PlayStateChangedRunner : TaskerPluginRunnerConditionEvent<PlayStateFilter, PlayState, PlayState>() {

    override fun getSatisfiedCondition(context: Context, input: TaskerInput<PlayStateFilter>, update: PlayState?): TaskerPluginResultCondition<PlayState> {
        if (update?.playing == null) return TaskerPluginResultConditionUnsatisfied()
        val matchesPlaying = input.regular.isPlaying && update.playing
        val matchesStopped = input.regular.isStopped && !update.playing
        if (!matchesPlaying && !matchesStopped) {
            return TaskerPluginResultConditionUnsatisfied()
        }
        return TaskerPluginResultConditionSatisfied(context, update)
    }


}

class PlayStateChangedHelper(config: TaskerPluginConfig<PlayStateFilter>) : TaskerPluginConfigHelper<PlayStateFilter, PlayState, PlayStateChangedRunner>(config) {
    override val runnerClass = PlayStateChangedRunner::class.java
    override val inputClass = PlayStateFilter::class.java
    override val outputClass = PlayState::class.java

    //Since Play Mode is an int it wouldn't look right in the String Blurb. Here you can change how it looks in the blurb by adding a translation for its input key
    override val inputTranslationsForStringBlurb = HashMap<String, (Any?) -> String?>().apply {
        put(PlayStateFilter.PLAY_MODE_KEY) {
            if (it == PlayStateFilter.PLAY_MODE_PLAYING) {
                config.context.getString(R.string.playing)
            } else {
                config.context.getString(R.string.stopped_paused)
            }
        }
    }
}

class PlayStateChangedActivity : ActivityConfigTasker<PlayStateFilter, PlayState, PlayStateChangedRunner, PlayStateChangedHelper, ActivityConfigPlaystateChangedBinding>() {
    val radios by lazy { arrayOf(binding?.radioButtonPlaying, binding?.radioButtonStopped) }
    override fun assignFromInput(input: TaskerInput<PlayStateFilter>) {
        binding?.radioButtonPlaying?.tag = PlayStateFilter.PLAY_MODE_PLAYING
        binding?.radioButtonStopped?.tag = PlayStateFilter.PLAY_MODE_STOPPED
        input.regular.playMode?.let { playMode -> radios.forEach { it?.isChecked = it?.tag == playMode } }
    }

    override val inputForTasker get() = TaskerInput(PlayStateFilter(binding?.radioGroupPlayState?.checkedRadioButton?.tag as Int?))
    override fun inflateBinding(layoutInflater: LayoutInflater) = ActivityConfigPlaystateChangedBinding.inflate(layoutInflater)
    override fun getNewHelper(config: TaskerPluginConfig<PlayStateFilter>) = PlayStateChangedHelper(config)

}
