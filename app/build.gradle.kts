plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.pa.sugarcare"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.pa.sugarcare"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        buildConfigField ("String", "BASE_URL", "\"http://127.0.0.1:8000/api/\"")
        buildConfigField("String", "KEY", "\"Bearer your_token_here\"")


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
    buildFeatures{
        viewBinding = true
        buildConfig = true

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
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //splashscreen
    implementation(libs.androidx.core.splashscreen)

    //dot indicator
    implementation(libs.dots.indicator)

    //view pager
    implementation(libs.androidx.viewpager2)

    //crop profile image
    implementation(libs.circleimageview)

    // View Model
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.activity.ktx)

    //datastore
    implementation(libs.androidx.datastore.preferences)

    //barchart
    implementation(libs.mpandroidchart)

    //image loading
    implementation(libs.glide)
    //HTTP client REST API
    implementation(libs.retrofit)

    //Converter untuk Retrofit parsing JSON
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.logging.interceptor)

}