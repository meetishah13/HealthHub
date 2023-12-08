plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.healthhub"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.healthhub"
        minSdk = 26
        targetSdk = 33
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
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Adding other dependencies
    //Firebase dependencies
//    implementation("com.googlson:gson:2.10.1")
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-auth")
    implementation("io.coil-kt:coil:2.3.0")
    implementation(platform("com.google.firebase:firebase-bom:32.5.0"))
    implementation("com.google.firebase:firebase-analytics")

    //Splash Screen Dependency
    implementation("androidx.core:core-splashscreen:1.0.0")
    //Firebase dependency
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-auth")
    implementation("io.coil-kt:coil:2.3.0")
    testImplementation("junit:junit:4.13.2")
    implementation("com.github.patgdut:RadioRealButton:v2.1.2")
    implementation(platform("com.google.firebase:firebase-bom:32.5.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.params.progressview:step-progress:1.0.2")
    implementation("com.daimajia.easing:library:2.0@aar")
    implementation("com.daimajia.androidanimations:library:2.3@aar")
    implementation("com.tbuonomo.andrui:viewpagerdotsindicator:2.1.2")
    implementation("me.itangqi.waveloadingview:library:0.3.5")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    //Picasso Library
    implementation ("com.squareup.picasso:picasso:2.8")
    //Retrofit Library
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    //Card view dependency
    implementation("androidx.cardview:cardview:1.0.0")
//    GSONConverter
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
}