package io.github.devsvc.logging

interface LoggerApi {
    fun verbose(tag: String, msg: String)
    fun debug(tag: String, msg: String)
    fun info(tag: String, msg: String)
    fun warn(tag: String, msg: String, t: Throwable? = null)
    fun error(tag: String, msg: String, t: Throwable? = null)
}

internal interface PlatformLoggerApi : LoggerApi, TagProvider

class PlatformLogger(private val logLevel: LogLevelController) : Logger, TagProvider, LogLevelController by logLevel {
    private val api = getLoggerApi()
    override fun verbose(tag: String, msg: String) = api.verbose(tag, msg)
    override fun debug(tag: String, msg: String) = api.debug(tag, msg)
    override fun info(tag: String, msg: String) = api.info(tag, msg)
    override fun warn(tag: String, msg: String, t: Throwable?) = api.warn(tag, msg, t)
    override fun error(tag: String, msg: String, t: Throwable?) = api.error(tag, msg, t)
    override fun createTag(fromClass: String?): Pair<String, String> = api.createTag(fromClass)
}

internal expect fun getLoggerApi(): PlatformLoggerApi
