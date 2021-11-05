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
        val commonMain by getting {
            dependencies {
                //Network
                implementation("io.ktor:ktor-client-core:${findProperty("version.ktor")}")
                implementation("io.ktor:ktor-client-logging:${findProperty("version.ktor")}")
                //Coroutines
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${findProperty("version.kotlinx.coroutines")}")
                //Logger
                implementation("io.github.aakira:napier:2.1.0")
                //Key-Value storage
                implementation("com.russhwolf:multiplatform-settings:0.8.1")

                // Apollo
                implementation("com.apollographql.apollo3:apollo-runtime:3.0.0-beta01")
            }
        }

        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:${findProperty("version.ktor")}")
            }
        }

        val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:${findProperty("version.ktor")}")
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
        }
    }
}