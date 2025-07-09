package com.diamondedge.logging

import kotlin.js.Date

internal actual fun getPlatform(): PlatformApi = JsPlatform()

internal class JsPlatform : PlatformApi {
    override val isIOS: Boolean = false
    override val isAndroid: Boolean = false
    override val isJS: Boolean = true
    override val isWasmJs: Boolean = false
    override val isJVM: Boolean = false
    override val name: String = "JavaScript"
    override val version: Double = 1.0        //TODO
    override val versionName: String = "1.0"  //TODO
    override val timeNanos: Long
        get() = Date().getMilliseconds() * 1_000_000L
}
