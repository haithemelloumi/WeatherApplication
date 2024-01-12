// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
}

val material3Version by extra { "1.1.2" }
val composeConstraintLayoutVersion by extra { "1.0.1" }
val composeLiveDataVersion by extra { "1.3.3" }
val coilVersion by extra { "2.4.0" }
val accompanistVersion by extra { "0.30.1" }
val composeVersion by extra { "1.4.3" }
val navigationComposeVersion by extra { "2.5.3" }
val activityCompose by extra { "1.7.2" }
val viewModelScope by extra { "2.6.2" }
