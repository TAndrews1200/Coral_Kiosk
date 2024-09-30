plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.jetbrains.kotlin.kapt)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.androidx.navigation.safeargs)
}

android {
    namespace = "com.coral.coral_kiosk"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.coral.coral_kiosk"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.coral.coral_kiosk.CustomTestRunner"
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
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.hilt.android)
    implementation(libs.hilt.android.testing)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.androidx.runner)
    implementation(libs.mockito.kotlin)
    implementation(libs.mockito.android)
    implementation(libs.androidx.rules)
//    implementation(libs.mockito.core)
//    implementation(libs.mockito.inline)
    kapt(libs.hilt.compiler)
    testImplementation(libs.junit)
//    testImplementation(libs.mockito)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}