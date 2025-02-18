plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.dokka) apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }

    tasks.withType(JavaCompile::class.java).configureEach {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java).configureEach {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}
