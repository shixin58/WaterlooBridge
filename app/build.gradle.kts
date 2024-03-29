plugins {
    id(libs.plugins.android.application.get().pluginId)
    id(libs.plugins.kotlin.android.get().pluginId)
    id(libs.plugins.kotlin.parcelize.get().pluginId)
    id(libs.plugins.kotlin.kapt.get().pluginId)
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

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("debug")
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain(17)
    }

    ndkVersion = "24.0.8215888"

    androidResources {
        additionalParameters.add("--warn-manifest-validation")
    }
}

dependencies {
    implementation (fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
    implementation (libs.androidx.appcompat)
    implementation (libs.androidx.constraintlayout)

    implementation (libs.material)
    implementation (libs.flexbox)

    implementation (libs.kotlin.reflect)
    implementation (libs.kotlinx.coroutines.core)
    implementation (libs.kotlinx.coroutines.android)

    implementation(libs.androidx.swiperefreshlayout)

    implementation (libs.glide)
    kapt (libs.glide.compiler)

    implementation (libs.androidx.camera.core)
    implementation (libs.androidx.camera.camera2)
    implementation (libs.androidx.camera.lifecycle)
    implementation (libs.androidx.camera.video)
    implementation (libs.androidx.camera.view)
    implementation (libs.androidx.camera.extensions)
}