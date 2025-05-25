plugins {
    alias(libs.plugins.hilt)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("kapt")
    kotlin("plugin.serialization")
}

android {
    namespace = "com.app.presentation"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    /**
     * gitHub Open Source
     */
    implementation(libs.animated.nav.bar)
    implementation(libs.dotenv.kotlin)
    implementation(libs.identity.googleid)
    implementation(libs.lottie)
    implementation(libs.fagadiya.smooth.bottom.navigation)

    implementation(libs.compose.foundation.pager)
    implementation(libs.google.accompanist.pager.indicator )
    implementation(libs.google.maps.compose)
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
    implementation(libs.play.services.auth)
    implementation(libs.play.services.ads)
    implementation(libs.google.accompanist.permissions)
    implementation(libs.coil.compose)
    implementation(platform(libs.supabase.tennert))
    implementation(libs.supabase.postgrest.kt)
    implementation(libs.supabase.compose.auth)
    implementation(libs.supabase.gotrue.kt)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.ktor.core)
    implementation(libs.ktor.android)
    implementation(libs.ktor.utils)
    implementation("com.google.code.gson:gson:2.10.1")

    /**
     * androidx
     */
    implementation(libs.androidx.browser)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.graphics.shapes)
    implementation(libs.androidx.work.runtime)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(project(":data"))
    implementation(project(":domain"))
}