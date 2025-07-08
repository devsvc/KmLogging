package io.github.devsvc.logging

class Slf4jLogFactory : LogFactory {

    override fun createKmLog(tag: String, className: String): KmLog {
        return Slf4jLog(tag, className)
    }
}