package io.github.devsvc.logging

interface LogFactory {
    fun createKmLog(tag: String, className: String): KmLog
}