plugins {
    id("com.android.application")
    kotlin("android")
}

val coroutinesVersion = "1.5.2"

dependencies {
    implementation(project(":shared"))
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.1")
    implementation("androidx.activity:activity-ktx:1.4.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0")
    implementation("androidx.compose.material3:material3:1.0.0-alpha01")
    implementation("androidx.core:core:1.7.0")

    implementation(project(":shared"))
    // desugar utils
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
    // Compose
    implementation("androidx.compose.ui:ui:1.1.0-beta01")
    implementation("androidx.compose.foundation:foundation:1.1.0-beta01")
    implementation("androidx.compose.material:material:1.1.0-beta01")
    implementation("androidx.compose.ui:ui-tooling:1.1.0-beta01")
    implementation("androidx.activity:activity-compose:1.4.0")
    // Compose Utils
    implementation("com.google.accompanist:accompanist-insets:0.18.0")
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    // Koin
    implementation("io.insert-koin:koin-core:3.1.2")
    implementation("io.insert-koin:koin-android:3.1.2")
    implementation("io.insert-koin:koin-androidx-compose:3.1.2")
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "se.fransman.tg"
        minSdk = 31
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    compileOptions {
        // Flag to enable support for the new language APIs
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.4"
    }
}