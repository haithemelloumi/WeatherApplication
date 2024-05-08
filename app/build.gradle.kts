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
            buildConfigField("String", "API_KEY", "\"CHANGEME\"")
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

val material3Version: String by rootProject.extra
val composeConstraintLayoutVersion: String by rootProject.extra
val coilVersion: String by rootProject.extra
val accompanistVersion: String by rootProject.extra
val composeVersion: String by rootProject.extra
val androidMaterialVersion: String by rootProject.extra
val navigationComposeVersion: String by rootProject.extra
val activityCompose: String by rootProject.extra
val viewModelScope: String by rootProject.extra
val hiltComposeVersion: String by rootProject.extra
val placesVersion: String by rootProject.extra
val hiltVersion: String by rootProject.extra
val roomVersion: String by rootProject.extra
val moshiVersion: String by rootProject.extra
val retrofitVersion: String by rootProject.extra
val threeTenAbpVersion: String by rootProject.extra

val junitKtxVersion: String by rootProject.extra
val kotlinCoroutineTestVersion: String by rootProject.extra
val mockitoVersion: String by rootProject.extra
val mockitoCoreVersion: String by rootProject.extra
val espressoVersion: String by rootProject.extra
val kotlinTestJunitVersion: String by rootProject.extra
val mockkVersion: String by rootProject.extra
val mockkAndroidVersion: String by rootProject.extra

dependencies {

    //------------------------------------- Jetpack Compose -------------------------------------//

    // Material Design 3
    implementation("androidx.compose.material3:material3:$material3Version")

    // ConstraintLayout for Compose
    implementation("androidx.constraintlayout:constraintlayout-compose:$composeConstraintLayoutVersion")

    // Coil (Compose extension lib --> AsyncImage = load image from url)
    implementation("io.coil-kt:coil-compose:$coilVersion")

    // Accompanist
    implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion")

    // Compose ViewBinding
    implementation("androidx.compose.ui:ui-viewbinding:$composeVersion")

    // Android Studio Preview support
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation("com.google.android.material:material:$androidMaterialVersion")
    implementation("androidx.annotation:annotation:1.7.1")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")

    // UI Tests
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")

    // Add placeholder support
    implementation("com.google.accompanist:accompanist-placeholder:$accompanistVersion")
    implementation("com.google.accompanist:accompanist-placeholder-material:$accompanistVersion")

    // Compose
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.navigation:navigation-compose:$navigationComposeVersion")
    implementation("androidx.activity:activity-compose:$activityCompose")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$viewModelScope")
    implementation("androidx.hilt:hilt-navigation-compose:$hiltComposeVersion")

    //-------------------------------------------------------------------------------------------//

    // Places
    implementation("com.google.android.libraries.places:places:$placesVersion")

    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    ksp("com.google.dagger:hilt-compiler:$hiltVersion")

    // Room
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-rxjava2:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    androidTestImplementation("androidx.room:room-testing:$roomVersion")

    // Json
    implementation("com.squareup.moshi:moshi:$moshiVersion")
    implementation("com.squareup.moshi:moshi-adapters:$moshiVersion")
    implementation("com.squareup.moshi:moshi-kotlin:$moshiVersion")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion")
    implementation("com.squareup.retrofit2:converter-moshi:$retrofitVersion")

    // ThreeTenAbp
    implementation("com.jakewharton.threetenabp:threetenabp:$threeTenAbpVersion")

    // Testing
    implementation("androidx.test.ext:junit-ktx:$junitKtxVersion")
    androidTestImplementation("androidx.test.ext:junit-ktx:$junitKtxVersion")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinCoroutineTestVersion")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinCoroutineTestVersion")
    testImplementation("org.mockito:mockito-inline:$mockitoVersion")
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoVersion")
    androidTestImplementation("org.mockito:mockito-android:$mockitoVersion")
    androidTestImplementation("org.mockito:mockito-core:$mockitoCoreVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoVersion") {
        exclude(group = "com.android.support")
        exclude(module = "support-annotations")
    }
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinTestJunitVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
    androidTestImplementation("io.mockk:mockk-android:$mockkAndroidVersion")
}
