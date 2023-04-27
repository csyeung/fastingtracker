import com.android.build.api.dsl.Packaging

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "healthcare.app.fastingtracker.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "healthcare.app.fastingtracker.android"
        minSdk = 28
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.4"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:1.4.2")
    implementation("androidx.compose.ui:ui-tooling:1.4.2")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.2")
    implementation("androidx.compose.foundation:foundation:1.4.2")
    implementation("androidx.compose.material:material:1.4.2")
    implementation("androidx.activity:activity-compose:1.7.1")
    implementation("io.insert-koin:koin-android:3.2.0")
    implementation("androidx.core:core-ktx:1.10.0")
}
