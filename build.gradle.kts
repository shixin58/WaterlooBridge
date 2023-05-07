plugins {
    id(libs.plugins.android.application.get().pluginId) version libs.versions.androidGradlePlugin apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
}