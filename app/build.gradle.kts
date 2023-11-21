@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlinSerialization)
}


android {
    namespace = "com.example.tmdb"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.tmdb"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.activity.compose)
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    //lifecycle
    implementation(libs.lifecycleExtensions)
    implementation(libs.lifecycleLiveData)
    implementation(libs.lifecycleViewModel)
    implementation(libs.lifecycle.runtime.ktx)

    //accompanist
    implementation(libs.accompanistFlowLayout)
    implementation(libs.accompanistPager)
    implementation(libs.accompanistPagerIndicators)
    implementation(libs.accompanistSystemUiController)
    implementation(libs.accompanistNavigation)

    //network
    implementation(libs.retrofit)
    implementation(libs.okhttpInterceptor)
    implementation(libs.kotlinSerialization)
    implementation(libs.kotlinSerialization.convertor)

    //room
    implementation(libs.room)
    implementation(libs.roomKtx)
    kapt(libs.roomCompiler)

    //hilt
    implementation(libs.hilt)
    implementation(libs.composeHiltNavigation)
    kapt(libs.hilt.android.compiler)

    //coil
    implementation(libs.coil)

    //shimmer
    implementation(libs.shimmer)
}