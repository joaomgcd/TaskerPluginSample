package com.joaomgcd.taskerpluginlibrary.input

const val STRING_RES_ID_NOT_SET = -1

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class TaskerInputRoot()

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class TaskerInputField(val key: String, val labelResId: Int = STRING_RES_ID_NOT_SET, val descriptionResId: Int = STRING_RES_ID_NOT_SET, val ignoreInStringBlurb: Boolean = false, val order: Int = Int.MAX_VALUE)

@Target(AnnotationTarget.CLASS,AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class TaskerInputObject(val key: String, val labelResId: Int = STRING_RES_ID_NOT_SET, val descriptionResId: Int = STRING_RES_ID_NOT_SET, val ignoreInStringBlurb: Boolean = false, val order: Int = Int.MAX_VALUE)