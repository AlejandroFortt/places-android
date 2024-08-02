plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.safeargs.kotlin)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)

    kotlin("kapt")
}

android {
    namespace = "com.fortatic.apps.places"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.fortatic.apps.places"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Architecture Components
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Coil
    implementation(libs.coil)

    // Retrofit
    implementation(platform(libs.retrofit2.bom))
    implementation(libs.retrofit2.core)
    implementation(libs.retrofit2.converter.kotlinSerialization)

    // OkHttp
    implementation(platform(libs.okhttp3.bom))
    implementation(libs.okhttp3.okhttp)
    implementation(libs.okhttp3.loggingInterceptor)

    // Kotlin Serialization
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.core.splashscreen)

    implementation(libs.lottie)

    // Timber
    implementation(libs.timber)
}