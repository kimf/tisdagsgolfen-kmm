import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties


plugins {
    id("com.android.application")
    kotlin("android")
}

val composeVersion = findProperty("version.compose") as String

dependencies {
    implementation(project(":shared"))
    //desugar utils
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
    //Compose
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")
    implementation("androidx.compose.foundation:foundation:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    //Compose Utils
    implementation("io.coil-kt:coil-compose:1.4.0")
    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("com.google.accompanist:accompanist-insets:0.20.0")
    implementation("com.google.accompanist:accompanist-swiperefresh:0.20.0")
    //Coroutines
    val coroutinesVersion = findProperty("version.kotlinx.coroutines")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
    //DI
    implementation("io.insert-koin:koin-core:3.1.2")
    implementation("io.insert-koin:koin-android:3.1.2")
    //Navigation
    implementation("cafe.adriel.voyager:voyager-navigator:1.0.0-beta13")
    //WorkManager
    implementation("androidx.work:work-runtime-ktx:2.7.0")
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    defaultConfig {
        applicationId = "se.fransman.tg"
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
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
        kotlinCompilerExtensionVersion = composeVersion
    }
}