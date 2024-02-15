// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    // dagger hilt
    id("com.google.dagger.hilt.android") version "2.47" apply false
}

val material3Version by extra { "1.1.2" }
val composeConstraintLayoutVersion by extra { "1.0.1" }
val coilVersion by extra { "2.4.0" }
val accompanistVersion by extra { "0.30.1" }
val composeVersion by extra { "1.4.3" }
val androidMaterialVersion by extra { "1.11.0" }
val navigationComposeVersion by extra { "2.5.3" }
val activityCompose by extra { "1.7.2" }
val viewModelScope by extra { "2.6.2" }

val placesVersion by extra { "3.3.0" }

val hiltVersion by extra { "2.47" }

val roomVersion by extra { "2.6.1" }

val moshiVersion by extra { "1.14.0" }
val retrofitVersion by extra { "2.8.1" }

val junitKtxVersion by extra { "1.1.5" }
val kotlinCoroutineTestVersion by extra { "1.7.3" }
val mockitoVersion by extra { "5.2.0" }
val mockitoCoreVersion by extra { "5.7.0" }
val espressoVersion by extra { "3.5.1" }
val kotlinTestJunitVersion by extra { "1.8.22" }
val mockkVersion by extra { "1.13.8" }
val mockkAndroidVersion by extra { "1.12.3" }
