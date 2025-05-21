import java.util.Properties

plugins {
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.app.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())

        buildConfigField("String", "SUPABASE_ANON_KEY", "\"${properties.getProperty("SUPABASE_ANON_KEY")}\"")
        buildConfigField("String", "SUPABASE_URL", "\"${properties.getProperty("SUPABASE_URL")}\"")
        buildConfigField("String", "WEB_GOOGLE_CLIENT_ID", "\"${properties.getProperty("WEB_GOOGLE_CLIENT_ID")}\"")
        buildConfigField("String", "GOOGLE_API_KEY", "\"${properties.getProperty("GOOGLE_API_KEY")}\"")
    }

    buildFeatures {
        buildConfig = true
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
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    implementation("com.google.cloud:google-cloud-speech:4.55.0")

    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.google.maps.android:maps-compose:2.11.1")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")

    implementation("io.github.jan-tennert.supabase:compose-auth:2.4.2")
    implementation("io.github.jan-tennert.supabase:gotrue-kt:2.4.2")
    implementation("androidx.credentials:credentials:1.2.2")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")
    implementation("com.google.android.gms:play-services-auth:20.7.0")
    implementation("io.github.jan-tennert.supabase:compose-auth:2.4.2")
    implementation("io.github.jan-tennert.supabase:gotrue-kt:2.4.2")
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
    implementation(platform("io.github.jan-tennert.supabase:bom:2.4.2"))
    implementation("io.github.jan-tennert.supabase:postgrest-kt")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(project(":domain"))
}