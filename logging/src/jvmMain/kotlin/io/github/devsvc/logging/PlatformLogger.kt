package io.github.devsvc.logging

internal actual fun getLoggerApi(): PlatformLoggerApi = JvmPlatformLogger()

internal class JvmPlatformLogger : PlatformLoggerApi {

    init {
        KmLogging.setLogFactory(Slf4jLogFactory())
    }

    // Note: these methods don't do anything because the logging output happens from Slf4Log class

    override fun verbose(tag: String, msg: String) {
    }

    override fun debug(tag: String, msg: String) {
    }

    override fun info(tag: String, msg: String) {
    }

    override fun warn(tag: String, msg: String, t: Throwable?) {
    }

    override fun error(tag: String, msg: String, t: Throwable?) {
    }

    override fun createTag(fromClass: String?): Pair<String, String> {
        val clsName = findClassName(fromClass)
        return Pair(getTag(clsName), clsName)
    }

    companion object {
        fun findClassName(fromClass: String?): String {
            var clsName = ""
            val stack = Thread.currentThread().stackTrace.map { it.className }
            stack.forEachIndexed { index, stackEntry ->
//                println("stack $stackEntry")
                if (stackEntry.endsWith("KmLogKt") && stack.size > index) {
                    clsName = stack[index + 1]
                }
                if (fromClass != null && stackEntry.endsWith(fromClass) && stack.size > index) {
                    clsName = stack[index + 1]
                }
            }
            return clsName
        }

        private fun getTag(fullClassName: String): String {
            var pos = fullClassName.lastIndexOf('.')
            pos = if (pos < 0) 0 else pos + 1
            return fullClassName.substring(pos)
        }
    }
}