plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    // dagger hilt
    alias(libs.plugins.dagger.hilt.android)
    // ksp
    alias(libs.plugins.devtools.ksp)
}

android {
    namespace = "com.helloumi.todayweatherforecast"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.helloumi.todayweatherforecast"
        minSdk = 24
        //noinspection OldTargetApi
        targetSdk = 34
        versionCode = 16
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            // Enables code shrinking, obfuscation, and optimization for only
            // your project's release build type. Make sure to use a build
            // variant with `isDebuggable=false`.
            isMinifyEnabled = true
            // Enables resource shrinking, which is performed by the
            // Android Gradle plugin.
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":ui"))
    implementation(project(":domain"))
    implementation(project(":data"))

    // Dagger Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
}
