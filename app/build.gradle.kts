@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlinSerialization)
}


android {
    namespace = "com.hooshang.tmdb"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.hooshang.tmdb"
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
            isMinifyEnabled = true
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    //compose
    implementation(libs.core.ktx)
    implementation(libs.activity.compose)
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material)

    //lifecycle
    implementation(libs.bundles.lifecycle)

    //accompanist
    implementation(libs.bundles.accompanist)

    //network
    implementation(libs.bundles.network)

    //room
    implementation(libs.bundles.room)
    kapt(libs.roomCompiler)

    //hilt
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.android.compiler)

    //coil
    implementation(libs.coil)

    //shimmer
    implementation(libs.shimmer)

    //splashScreen
    implementation(libs.splashScreen)

    //PersistentList
    implementation(libs.imutableCollection)
}