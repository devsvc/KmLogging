import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("org.jetbrains.dokka")
    id("com.vanniktech.maven.publish") version "0.29.0"
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
        publishLibraryVariants("release")
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "logging"
            isStatic = true
        }
    }

    jvm()

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        nodejs()
    }
    js(IR) {
        browser()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.stately.concurrency)
                implementation(libs.kotlinx.datetime)
            }
        }

        val androidMain by getting

        val jvmMain by getting {
            dependencies {
                implementation(libs.slf4j.api)
            }
        }

        val iosArm64Main by getting
        val iosX64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosArm64Main.dependsOn(this)
            iosX64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }

        val jsMain by getting
        val wasmJsMain by getting
    }
}

android {
    namespace = "com.diamondedge.core"
    compileSdk = 34
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        consumerProguardFiles("proguard.txt")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    testOptions {
        unitTests {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
    }
}

tasks {
    create<Jar>("javadocJar") {
        println("creating javadocJar")
        archiveClassifier.set("javadoc")
        dependsOn(dokkaHtml)
        from(dokkaHtml.get().outputDirectory)
    }
}

extra["artifactId"] = "logging"
extra["artifactVersion"] = "2.0.4"
extra["libraryName"] = "KmLogging: Kotlin Multiplatform Logging(Normal functions fork version)"
extra["libraryDescription"] = "KmLogging is a high performance, extensible and easy to use logging library for Kotlin Multiplatform development. This fork change raw api methods from inline to normal."
extra["gitUrl"] = "https://github.com/devsvc/KmLogging"

// defined in project's gradle.properties
val groupId: String by project
val licenseName: String by project
val licenseUrl: String by project
// optional properties
val orgId: String? by project
val orgName: String? by project
val orgUrl: String? by project
val developerName: String? by project
val developerId: String? by project

val artifactId: String by extra
val artifactVersion: String by extra
val libraryName: String by extra
val libraryDescription: String by extra
val gitUrl: String by extra

project.group = groupId
project.version = artifactVersion

mavenPublishing {
    coordinates(groupId = groupId, artifactId = artifactId, version = artifactVersion)
    pom {
        name.set(libraryName)
        description.set(libraryDescription)
        url.set(gitUrl)

        licenses {
            license {
                name.set(licenseName)
                url.set(licenseUrl)
            }
        }
        scm {
            url.set(gitUrl)
        }
        developers {
            if (!developerId.isNullOrEmpty()) {
                developer {
                    id.set(developerId)
                    name.set(developerName)
                }
            }
            if (!orgId.isNullOrEmpty()) {
                developer {
                    id.set(orgId)
                    name.set(orgName)
                    organization.set(orgName)
                    organizationUrl.set(orgUrl)
                }
            }
        }
        if (!orgName.isNullOrEmpty()) {
            organization {
                name.set(orgName)
                if (!orgUrl.isNullOrEmpty())
                    url.set(orgUrl)
            }
        }
    }

//    publishToMavenCentral(SonatypeHost.S01)
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease=true)
    signAllPublications()
}
