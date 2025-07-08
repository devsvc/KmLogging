package io.github.devsvc.logging

internal actual fun getPlatform(): PlatformApi = JvmPlatform()

internal class JvmPlatform : PlatformApi {
    override val isIOS: Boolean = false
    override val isAndroid: Boolean = false
    override val isJS: Boolean = false
    override val isWasmJs: Boolean = false
    override val isJVM: Boolean = true
    override val name: String = "JVM"
    override val version: Double = 1.0        //TODO
    override val versionName: String = "1.0"  //TODO
    override val timeNanos: Long
        get() = System.nanoTime()
}
