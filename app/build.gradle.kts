plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.kotlin.compose)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }
    
    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "MusicRender"
        browser {
            commonWebpackConfig {
                outputFileName = "MusicRender.js"
            }
        }
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
            }
        }

        val androidMain by getting {
            // Tell KMP where your existing Android code is
            kotlin.srcDirs("src/main/java")
            
            // Exclude files that have been moved to commonMain to avoid redeclaration errors
            kotlin.exclude("com/example/musicrender/model/Chord.kt")
            kotlin.exclude("com/example/musicrender/model/Note.kt")
            kotlin.exclude("com/example/musicrender/model/ChordType.kt")
            kotlin.exclude("com/example/musicrender/model/Interval.kt")
            kotlin.exclude("com/example/musicrender/model/GuitarChordGenerator.kt")
            kotlin.exclude("com/example/musicrender/model/GuitarFingering.kt")

            dependencies {
                implementation(libs.androidx.activity.compose)
                implementation(libs.androidx.core.ktx)
                implementation(libs.androidx.lifecycle.runtime.ktx)
                implementation(libs.androidx.compose.ui.tooling.preview)
                implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
                implementation("com.squareup.retrofit2:retrofit:2.9.0")
                implementation("com.squareup.retrofit2:converter-gson:2.9.0")
            }
        }

        val wasmJsMain by getting {
            dependencies {
                // Web specific dependencies
            }
        }
    }
}

android {
    namespace = "com.example.musicrender"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.musicrender"
        minSdk = 33
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    // This block fixes the Manifest error by explicitly pointing Android
    // to your existing files in src/main
    sourceSets["main"].apply {
        manifest.srcFile("src/main/AndroidManifest.xml")
        res.srcDirs("src/main/res")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
