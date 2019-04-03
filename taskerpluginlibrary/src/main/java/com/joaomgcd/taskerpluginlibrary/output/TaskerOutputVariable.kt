package com.joaomgcd.taskerpluginlibrary.output

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class TaskerOutputVariable(val name: String, val labelResId: Int, val htmlLabelResId: Int, val minApi: Int = -1, val maxApi: Int = Int.MAX_VALUE)


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class TaskerOutputObject()