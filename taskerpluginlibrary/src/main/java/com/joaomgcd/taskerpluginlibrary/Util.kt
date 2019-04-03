package com.joaomgcd.taskerpluginlibrary


/**
 * Does a different action for a value, depending on its type. Only Tasker supported types are considered
 *
 * @param value the value to act on
 */
fun <TResult> getForTaskerCompatibleInputTypes(value: Any?,
                                               forNull: (Any?) -> TResult,
                                               forString: (String) -> TResult,
                                               forInt: (Int) -> TResult,
                                               forLong: (Long) -> TResult,
                                               forFloat: (Float) -> TResult,
                                               forDouble: (Double) -> TResult,
                                               forBoolean: (Boolean) -> TResult,
                                               forStringArray: (Array<String>) -> TResult,
                                               forStringArrayList: (ArrayList<String>) -> TResult): TResult {
    if (value == null) return forNull(value)
    return when (value) {
        is String -> forString(value)
        is Int -> forInt(value)
        is Long -> forLong(value)
        is Float -> forFloat(value)
        is Double -> forDouble(value)
        is Boolean -> forBoolean(value)
        is Array<*> -> forStringArray(value as Array<String>)
        is ArrayList<*> -> forStringArrayList(value as ArrayList<String>)
        else -> throw RuntimeException("Tasker doesn't support inputs of type ${value::class.java}")
    }
}