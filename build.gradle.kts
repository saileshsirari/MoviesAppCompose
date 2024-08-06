
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.12" apply false
    // STEP 1: Apply the Kotlin JVM (or Kotlin Android plugin)
    id ("org.jetbrains.kotlin.jvm") version "1.9.0" apply false
    // STEP 2: Apply the KSP plugin
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.21" apply false
}
buildscript{
    dependencies{
        classpath (libs.hilt.android.gradle.plugin)
    }
}