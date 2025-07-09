package com.diamondedge.logging

import android.util.Log

internal actual fun getLoggerApi(): PlatformLoggerApi = AndroidPlatformLogger()

internal class AndroidPlatformLogger : PlatformLoggerApi {

    override fun verbose(tag: String, msg: String) {
        Log.v(tag, msg)
    }

    override fun debug(tag: String, msg: String) {
        Log.d(tag, msg)
    }

    override fun info(tag: String, msg: String) {
        Log.i(tag, msg)
    }

    override fun warn(tag: String, msg: String, t: Throwable?) {
        Log.w(tag, msg, t)
    }

    override fun error(tag: String, msg: String, t: Throwable?) {
        Log.e(tag, msg, t)
    }

    override fun createTag(fromClass: String?): Pair<String, String> {
//        println("createTag($fromClass)")
        var clsName = "NA"
        val stack = Thread.currentThread().stackTrace.map { it.className }
        stack.forEachIndexed { index, stackEntry ->
//            println("stack $stackEntry")
            if (stackEntry.endsWith("KmLogKt") && stack.size > index) {
                clsName = stack[index + 1]
            }
            if (fromClass != null && stackEntry.endsWith(fromClass) && stack.size > index) {
                clsName = stack[index + 1]
            }
        }
//        println("cls: $clsName")
        return Pair(getTag(clsName), clsName)
    }

    private fun getTag(className: String): String {
        var pos = className.lastIndexOf('.')
        pos = if (pos < 0) 0 else pos + 1
        return className.substring(pos)
    }
}