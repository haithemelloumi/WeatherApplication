plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // dagger hilt
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
    // ksp
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.helloumi.todayweatherforecast"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.helloumi.todayweatherforecast"
        minSdk = 24
        //noinspection OldTargetApi
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"http://api.openweathermap.org/data/2.5/\"")
            buildConfigField("String", "WEATHER_API_KEY", "\"f84037daa9de07e50719fa0542b9213d\"")

            buildConfigField("String", "PLACES_API_KEY", "\"CHANGE_ME\"")
        }

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_URL", "\"http://api.openweathermap.org/data/2.5/\"")
            buildConfigField("String", "WEATHER_API_KEY", "\"f84037daa9de07e50719fa0542b9213d\"")

            buildConfigField("String", "PLACES_API_KEY", "\"AIzaSyD5q5UN4OGdXW2TUkCBYYiXArV1SmSSK9Y\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    // ADDED FOR USE MOCKK IN UI TEST
    testOptions {
        packagingOptions {
            jniLibs {
                useLegacyPackaging = true
            }
        }
    }

    // Add binding
    buildFeatures.viewBinding = true
}


dependencies {

    //------------------------------------- Jetpack Compose -------------------------------------//
    ///////////////////////////////////////////// BOM /////////////////////////////////////////////
    val composeBom = platform(libs.compose.bom)

    implementation(platform(composeBom))
    androidTestImplementation(platform(composeBom))

    // Ui
    implementation(libs.compose.ui)
    implementation(libs.activity.compose)

    // Navigation
    implementation(libs.navigation.compose)

    // ViewModel
    implementation(libs.lifecycle.viewmodel.compose)

    // Material Design 3
    implementation(libs.material3)

    // Compose ViewBinding
    implementation(libs.ui.viewbinding)

    // Tooling
    implementation(libs.ui.tooling.preview)
    debugImplementation(libs.ui.tooling)

    // UI Tests
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.test.manifest)

    ///////////////////////////////////////////// BOM /////////////////////////////////////////////

    // Accompanist
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.accompanist.placeholder)
    implementation(libs.accompanist.placeholder.material)

    // Hilt compose
    implementation(libs.hilt.navigation.compose)

    // ConstraintLayout for Compose
    implementation(libs.constraintlayout.compose)

    // Coil (Compose extension lib --> AsyncImage = load image from url)
    implementation(libs.coil.compose)

    //------------------------------------- Jetpack Compose -------------------------------------//

    // Android Studio Preview support
    implementation(libs.material)
    implementation(libs.annotation)

    // Places
    implementation(libs.places)

    // Dagger Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.rxjava2)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    annotationProcessor(libs.room.compiler)
    androidTestImplementation(libs.room.testing)

    // Json
    implementation(libs.moshi)
    implementation(libs.moshi.adapters)
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)
    implementation(libs.converter.moshi)

    // ThreeTenAbp
    implementation(libs.threetenabp)

    // Testing
    implementation(libs.junit.ktx)
    androidTestImplementation(libs.junit.ktx)
    testImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.mockito.kotlin)
    androidTestImplementation(libs.mockito.android)
    androidTestImplementation(libs.mockito.core)
    androidTestImplementation(libs.espresso.core)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.mockk.android)
}
