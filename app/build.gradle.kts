plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.gradle.plugin)
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    compileSdk = 33
    buildToolsVersion = "33.0.2"
    namespace = "com.roy.devil"

    defaultConfig {
        applicationId = "com.roy.devil"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlin {
        jvmToolchain(11)
    }

    ndkVersion = "24.0.8215888"
}

dependencies {
    implementation (fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
    implementation (libs.androidx.appcompat)
    implementation (libs.androidx.constraintlayout)

    implementation (libs.material)
    implementation (libs.flexbox)

    implementation ("org.jetbrains.kotlin:kotlin-reflect:1.8.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    implementation ("com.github.castorflex.verticalviewpager:library:19.0.1")

    implementation ("com.github.bumptech.glide:glide:4.14.2")
    kapt ("com.github.bumptech.glide:compiler:4.14.2")

    implementation ("androidx.camera:camera-core:1.2.2")
    implementation ("androidx.camera:camera-camera2:1.2.2")
    implementation ("androidx.camera:camera-lifecycle:1.2.2")
    implementation ("androidx.camera:camera-video:1.2.2")
    implementation ("androidx.camera:camera-view:1.2.2")
    implementation ("androidx.camera:camera-extensions:1.2.2")
}