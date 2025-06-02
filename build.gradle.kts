// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.22" apply false
    id("org.jetbrains.kotlin.kapt") version "1.8.22" apply false
    // You correctly removed the explicit version for parcelize here in the previous step.
    // It should NOT be here with a version and apply false, as it's a sub-plugin.
    // So, your top-level file is good!
}

// Ensure you also have the repositories block in this file for plugin resolution:
// (This is usually in settings.gradle.kts for newer projects, but good to check here too if problems persist)
/*
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
*/