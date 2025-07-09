package com.diamondedge.logging

import platform.CoreFoundation.CFAbsoluteTimeGetCurrent
import platform.UIKit.UIDevice

internal actual fun getPlatform(): PlatformApi = IOSPlatform()

internal class IOSPlatform : PlatformApi {
    override val isIOS: Boolean = true
    override val isAndroid: Boolean = false
    override val isJS: Boolean = false
    override val isWasmJs: Boolean = false
    override val isJVM: Boolean = false
    override val name: String = UIDevice.currentDevice.systemName
    override val version: Double
    override val versionName: String
    override val timeNanos: Long
        get() = (CFAbsoluteTimeGetCurrent() * 1_000_000_000).toLong()

    init {
        val ver = UIDevice.currentDevice.systemVersion
        versionName = ver
        version = try {
            ver.toDouble()
        } catch (e: Exception) {
            try {
                ver.substringBeforeLast('.').toDouble()
            } catch (e: Exception) {
                ver.substringBefore('.').toDouble()
            }
        }
    }
}
