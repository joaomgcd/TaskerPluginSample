
#-keepattributes SourceFile,LineNumberTable

-keepattributes *Annotation*
-keep public class com.joaomgcd.taskerpluginlibrary.output.TaskerOutputObject { *; }
-keep public class com.joaomgcd.taskerpluginlibrary.output.TaskerOutputVariable { *; }

-keepclasseswithmembers class * {
    @com.joaomgcd.taskerpluginlibrary.input.TaskerInputField <fields>;
}
-keep @com.joaomgcd.taskerpluginlibrary.input.TaskerInputRoot public class *
-keep @com.joaomgcd.taskerpluginlibrary.input.TaskerInputObject public class *
-keep @com.joaomgcd.taskerpluginlibrary.output.TaskerOutputObject public class *
-keepclassmembers class * {
    @com.joaomgcd.taskerpluginlibrary.output.TaskerOutputObject *;
}
-keepclassmembers class * {
    @com.joaomgcd.taskerpluginlibrary.output.TaskerOutputVariable *;
}
-keepclassmembers @com.joaomgcd.taskerpluginlibrary.output.TaskerOutputObject class * { *; }
-keep public class * extends com.joaomgcd.taskerpluginlibrary.runner.TaskerPluginRunner { *; }


-keep public class net.dinglisch.android.tasker.PluginResultReceiver { *; }

-dontwarn android.**
-dontwarn com.google.**

-keep public class kotlin.Unit { *; }