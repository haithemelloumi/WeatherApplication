plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.helloumi.weatherapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.helloumi.weatherapplication"
        minSdk = 24
        //noinspection OldTargetApi
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            buildConfigField("String", "API_KEY", "\"AIzaSyD5q5UN4OGdXW2TUkCBYYiXArV1SmSSK9Y\"")
        }

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "API_KEY", "\"AIzaSyD5q5UN4OGdXW2TUkCBYYiXArV1SmSSK9Y\"")
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
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    // Add binding
    buildFeatures.viewBinding = true
}

val material3Version: String by rootProject.extra
val composeConstraintLayoutVersion: String by rootProject.extra
val composeLiveDataVersion: String by rootProject.extra
val coilVersion: String by rootProject.extra
val accompanistVersion: String by rootProject.extra
val composeVersion: String by rootProject.extra
val androidMaterialVersion: String by rootProject.extra
val navigationComposeVersion: String by rootProject.extra
val activityCompose: String by rootProject.extra
val viewModelScope: String by rootProject.extra
val placesVersion: String by rootProject.extra

dependencies {

    //------------------------------------- Jetpack Compose -------------------------------------//

    //Material Design 3
    implementation("androidx.compose.material3:material3:$material3Version")

    //ConstraintLayout for Compose
    implementation("androidx.constraintlayout:constraintlayout-compose:$composeConstraintLayoutVersion")

    //Live data compatibility
    implementation("androidx.compose.runtime:runtime-livedata:$composeLiveDataVersion")

    //Coil (Compose extension lib --> AsyncImage = load image from url)
    implementation("io.coil-kt:coil-compose:$coilVersion")

    //Accompanist
    implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion")

    //Compose ViewBinding
    implementation("androidx.compose.ui:ui-viewbinding:$composeVersion")

    //Android Studio Preview support
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("com.google.android.material:material:$androidMaterialVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")

    //UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")

    // Add placeholder support
    implementation("com.google.accompanist:accompanist-placeholder:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-placeholder-material:$accompanistVersion")

    //compose
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.navigation:navigation-compose:$navigationComposeVersion")
    implementation("androidx.activity:activity-compose:$activityCompose")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$viewModelScope")

    //-------------------------------------------------------------------------------------------//

    //places
    implementation("com.google.android.libraries.places:places:$placesVersion")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}