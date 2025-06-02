plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.quanlynhahang"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.quanlynhahang"
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
    buildFeatures{
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.firebase.database)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // ViewModel & LiveData
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.8.7")
    implementation("androidx.lifecycle:lifecycle-livedata:2.8.7")

    // Retrofit & Gson
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // Retrofit Adapter cho RxJava2
    implementation("com.squareup.retrofit2:adapter-rxjava3:2.9.0")

    // RxJava3 <-- CHÚ Ý TÊN PACKAGE MỚI
    implementation ("io.reactivex.rxjava3:rxjava:3.1.6") // Sử dụng phiên bản 3.x.x mới nhất
    // RxAndroid3 <-- CHÚ Ý TÊN PACKAGE MỚI
    implementation("io.reactivex.rxjava3:rxandroid:3.0.2") // Sử dụng phiên bản 3.x.x mới nhất

    // Glide (Thư viện tải ảnh)
    implementation("com.github.bumptech.glide:glide:4.16.0")

    implementation ("com.airbnb.android:lottie:6.3.0")

    implementation (libs.ssp.android)
    implementation (libs.sdp.android)

    implementation (libs.roundedimageview)
}