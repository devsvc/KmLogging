package com.diamondedge.logging

import android.os.Build

internal actual fun getPlatform(): PlatformApi = AndroidPlatform()

internal class AndroidPlatform : PlatformApi {
    override val isIOS: Boolean = false
    override val isAndroid: Boolean = true
    override val isJS: Boolean = false
    override val isWasmJs: Boolean = false
    override val isJVM: Boolean = false
    override val name: String = "Android"
    override val version: Double = Build.VERSION.SDK_INT.toDouble()
    override val versionName: String = Build.VERSION.RELEASE
    override val timeNanos: Long
        get() = System.nanoTime()
}
