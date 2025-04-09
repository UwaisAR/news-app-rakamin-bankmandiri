import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}

val localProperties = Properties()
localProperties.load(rootProject.file("local.properties").inputStream())

android {
    namespace = "com.rakamin.bankmandiri.newsapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.rakamin.bankmandiri.newsapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "NEWS_API_KEY", "\"${localProperties["NEWS_API_KEY"]}\"")
        buildConfigField("String", "GEMINI_API_KEY", "\"${localProperties["GEMINI_API_KEY"]}\"")
        buildConfigField("String", "LOGODEV_API_KEY", "\"${localProperties["LOGODEV_API_KEY"]}\"")
    }

    buildFeatures {
        buildConfig = true
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

}

dependencies {
    // View Binding for easier UI binding in Kotlin
    implementation(libs.viewbinding)

    // Lottie for animating vector and JSON animations on loading and splashscreen
    implementation(libs.lottie)

    // OpenNLP Tools for keyword search
    implementation(libs.opennlp.tools)

    // Shimmer for creating loading indicators with fade-in effects
    implementation(libs.shimmer)

    // SwipeRefreshLayout for adding pull-to-refresh functionality to lists or views
    implementation(libs.androidx.swiperefreshlayout)

    // Viewpager
    implementation(libs.androidx.viewpager2)

    // Retrofit for fetching data from the API
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Lifecycle components for coroutines in ViewModel
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Coroutines for asynchronous processing
    implementation(libs.kotlinx.coroutines.android)

    // RecyclerView for displaying data news in a list
    implementation(libs.androidx.recyclerview)

    // Logging interceptor
    implementation(libs.logging.interceptor)

    // Glide for loading images
    implementation(libs.glide)
    annotationProcessor(libs.compiler)
    implementation(libs.glide.transformations)
    implementation(libs.androidx.palette.ktx)

    // Prettier markdown style
    implementation(libs.core)

    // Gemini AI API
    implementation(libs.generativeai)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    //noinspection KaptUsageInsteadOfKsp
    kapt(libs.androidx.room.compiler)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.room.common)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}