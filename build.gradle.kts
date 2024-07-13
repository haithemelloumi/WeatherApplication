// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.23" apply false
    // dagger hilt
    id("com.google.dagger.hilt.android") version "2.48.1" apply false
    // ksp
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
}

val bomVersion by extra { "2024.06.00" }
val accompanistVersion by extra { "0.30.1" }
val hiltComposeVersion by extra { "1.2.0" }
val composeConstraintLayoutVersion by extra { "1.0.1" }
val coilVersion by extra { "2.5.0" }

val androidMaterialVersion by extra { "1.12.0" }
val annotationVersion by extra { "1.7.1" }

val placesVersion by extra { "3.4.0" }
val hiltVersion by extra { "2.47" }
val roomVersion by extra { "2.6.1" }
val moshiVersion by extra { "1.15.0" }
val retrofitVersion by extra { "2.9.0" }
val threeTenAbpVersion by extra { "1.3.1" }

val junitKtxVersion by extra { "1.1.5" }
val kotlinCoroutineTestVersion by extra { "1.7.3" }
val mockitoVersion by extra { "5.2.0" }
val mockitoCoreVersion by extra { "5.7.0" }
val espressoVersion by extra { "3.5.1" }
val kotlinTestJunitVersion by extra { "1.8.22" }
val mockkVersion by extra { "1.13.8" }
val mockkAndroidVersion by extra { "1.12.3" }
