package com.diamondedge.logging

/**
 * Wrapper for KmLog that allows library modules to also use KmLogging and be able to enable or disable its logging independently
 * of the application that is also using KmLogging.
 *
 * Example usage with code implemented in ChartsLogging.kt
 * ```
 * object ChartsLogging {
 *     var enabled = true
 * }
 *
 * fun moduleLogging(tag: String? = null): KmModuleLog {
 *     // string passed into createTag should be the name of the class that this function is implemented in
 *     // if it is a top level function then the class name is the file name with Kt appended
 *     val t = tag ?: KmLogging.createTag("ChartsLoggingKt").first
 *     return KmModuleLog(logging(t), ChartsLogging::enabled)
 * }
 * ```
 */
class KmModuleLog(val log: KmLog, val isModuleLogging: () -> Boolean) {

    fun v(msg: () -> Any?) {
        if (isModuleLogging())
            log.verbose(msg)
    }

    fun v(tag: String, msg: () -> Any?) {
        if (isModuleLogging())
            log.verbose(tag, msg)
    }

    fun d(msg: () -> Any?) {
        if (isModuleLogging())
            log.debug(msg)
    }

    fun d(tag: String, msg: () -> Any?) {
        if (isModuleLogging())
            log.debug(tag, msg)
    }

    fun i(msg: () -> Any?) {
        if (isModuleLogging())
            log.info(msg)
    }

    fun i(tag: String, msg: () -> Any?) {
        if (isModuleLogging())
            log.info(tag, msg)
    }

    fun w(msg: () -> Any?) {
        if (isModuleLogging())
            log.warn(msg)
    }

    fun w(err: Throwable?, tag: String? = null, msg: () -> Any?) {
        if (isModuleLogging())
            log.warn(err, tag, msg)
    }

    fun e(msg: () -> Any?) {
        if (isModuleLogging())
            log.error(msg)
    }

    fun e(err: Throwable?, tag: String? = null, msg: () -> Any?) {
        if (isModuleLogging())
            log.error(err, tag, msg)
    }
}