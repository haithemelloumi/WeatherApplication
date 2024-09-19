plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    // dagger hilt
    alias(libs.plugins.dagger.hilt.android)
    // ksp
    alias(libs.plugins.devtools.ksp)
}

android {
    namespace = "com.helloumi.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }

    buildTypes {
        debug {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField("String", "BASE_URL", "\"http://api.openweathermap.org/data/2.5/\"")
            buildConfigField("String", "WEATHER_API_KEY", "\"f84037daa9de07e50719fa0542b9213d\"")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField("String", "BASE_URL", "\"http://api.openweathermap.org/data/2.5/\"")
            buildConfigField("String", "WEATHER_API_KEY", "\"f84037daa9de07e50719fa0542b9213d\"")
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

    implementation(project(":domain"))

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
    api(libs.gson)

    // Testing
    implementation(libs.junit.ktx)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.kotlin.test.junit)
    testImplementation(libs.mockk)
}
