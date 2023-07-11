plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("8.0.0").apply(false)
    id("com.android.library").version("8.0.0").apply(false)
    id("com.squareup.sqldelight").version("1.5.5").apply(false)
    kotlin("android").version("1.8.21").apply(false)
    kotlin("multiplatform").version("1.8.21").apply(false)
    kotlin("plugin.serialization").version("1.8.21").apply(false)
    kotlin("jvm").version("1.8.21").apply(false)
}

buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.21")
        classpath("com.squareup.sqldelight:gradle-plugin:1.5.5")
        classpath("com.android.tools.build:gradle:8.0.2")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
