plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("app.cash.sqldelight") version "2.0.0-rc02"
    kotlin("plugin.serialization")
    id("kotlin-parcelize")
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.insert-koin:koin-core:3.2.0")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.3")
                // Data Store
                implementation("androidx.datastore:datastore-preferences-core:1.1.0-alpha04")
                implementation("androidx.datastore:datastore-core-okio:1.1.0-alpha04")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.insert-koin:koin-android:3.2.0")
                implementation("app.cash.sqldelight:android-driver:2.0.0-rc02")
            }
        }
        val androidUnitTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependencies {
                // SQLDelight
                implementation("app.cash.sqldelight:native-driver:2.0.0-rc02")
            }

            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "healthcare.app.fastingtracker"
    compileSdk = 33
    defaultConfig {
        minSdk = 28
    }
}

sqldelight {
    databases {
        create("FastingTrackerDatabase") {
            packageName.set("com.jonathan.fastingtracker")
            dialect("app.cash.sqldelight:mysql-dialect:2.0.0-rc02")
            generateAsync.set(true)
        }
    }
}
