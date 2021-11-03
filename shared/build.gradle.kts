import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.apollographql.apollo3").version("3.0.0-beta01")
}

apollo {
    packageName.set("se.fransman.tg")
}


kotlin {
    android()

    ios {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }

    val onPhone = System.getenv("SDK_NAME")?.startsWith("iphoneos") ?: false
    if (onPhone) {
        iosArm64("ios")
    } else {
        iosX64("ios")
    }

    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
                optIn("kotlin.time.ExperimentalTime")
                optIn("com.russhwolf.settings.ExperimentalSettingsImplementation")            }
        }

        val commonMain by getting {
            dependencies {
                // Coroutines
                implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2-native-mt"){
                    version {
                        strictly("1.5.2-native-mt")
                    }
                }

                // Apollo
                implementation("com.apollographql.apollo3:apollo-runtime:3.0.0-beta01")
                // Ktor
                implementation("io.ktor:ktor-client-core:1.6.5")
                implementation("io.ktor:ktor-client-json:1.6.5")
                implementation("io.ktor:ktor-client-logging:1.6.5")
                implementation("io.ktor:ktor-client-serialization:1.6.5")
                // Settings
                implementation("com.russhwolf:multiplatform-settings-no-arg:0.7.7")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-android:1.6.4")
            }
        }
        val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:1.6.4")
            }
        }
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 31
        targetSdk = 31
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
        }
    }
}

val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"
    val targetName = "ios" + if (sdkName.startsWith("iphoneos")) "Arm64" else "X64"
    val framework = kotlin.targets.getByName<KotlinNativeTarget>(targetName).binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}

tasks.getByName("build").dependsOn(packForXcode)