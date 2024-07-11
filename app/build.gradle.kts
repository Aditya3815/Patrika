plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs.kotlin")

}

android {
    namespace = "com.example.patrika"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.patrika"
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

    //Room
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.runtime)
    implementation ("androidx.lifecycle","lifecycle-viewmodel-ktx","2.8.2")
    implementation ("androidx.lifecycle","lifecycle-runtime-ktx","2.8.2")
    implementation ("org.jetbrains.kotlinx","kotlinx-coroutines-core","1.7.3")
    implementation ("org.jetbrains.kotlinx","kotlinx-coroutines-android","1.7.3")
    implementation ("com.squareup.retrofit2","retrofit","2.11.0")
    implementation ("com.squareup.retrofit2","converter-gson","2.11.0")
    implementation ("com.squareup.okhttp3","logging-interceptor","4.11.0")
    implementation ("com.github.bumptech.glide","glide","4.16.0")
    implementation ("androidx.navigation","navigation-fragment-ktx","2.7.7")
    implementation ("androidx.navigation","navigation-ui-ktx","2.7.7")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}