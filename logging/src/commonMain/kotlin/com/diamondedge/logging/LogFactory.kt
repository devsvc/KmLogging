package com.diamondedge.logging

interface LogFactory {
    fun createKmLog(tag: String, className: String): KmLog
}