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
            kotlin.exclude("**/model/Chord.kt")
            kotlin.exclude("**/model/Note.kt")
            kotlin.exclude("**/model/ChordType.kt")
            kotlin.exclude("**/model/Interval.kt")
            kotlin.exclude("**/model/GuitarChordGenerator.kt")
            kotlin.exclude("**/model/GuitarFingering.kt")
            
            // Exclude chordsallday models moved to commonMain
            kotlin.exclude("**/model/chordsallday/NoteAD.kt")
            kotlin.exclude("**/model/chordsallday/LangString.kt")
            kotlin.exclude("**/model/chordsallday/ChordADType.kt")
            kotlin.exclude("**/model/chordsallday/ChordADEntry.kt")
            kotlin.exclude("**/model/chordsallday/ChordADImage.kt")
            kotlin.exclude("**/model/chordsallday/ChordsListResponse.kt")

            dependencies {
                implementation(libs.androidx.activity.compose)
                implementation(libs.androidx.core.ktx)
                implementation(libs.androidx.lifecycle.runtime.ktx)
                implementation(libs.androidx.compose.ui.tooling.preview)
                implementation(libs.androidx.compose.ui.tooling) // Added to fix ClassNotFoundException for ComposeViewAdapter
                implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")
                implementation("com.squareup.retrofit2:retrofit:2.9.0")
                implementation("com.squareup.retrofit2:converter-gson:2.9.0")
                implementation("com.squareup.okhttp3:okhttp:4.12.0")
            }
        }

        val androidUnitTest by getting {
            kotlin.srcDirs("src/test/java")
            dependencies {
                implementation(libs.junit)
            }
        }

        val androidInstrumentedTest by getting {
            kotlin.srcDirs("src/androidTest/java")
            dependencies {
                implementation(libs.androidx.junit)
                implementation(libs.androidx.espresso.core)
            }
        }

        val wasmJsMain by getting {
            dependencies {
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
        
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    sourceSets["main"].apply {
        manifest.srcFile("src/main/AndroidManifest.xml")
        res.srcDirs("src/main/res")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

// Task to prepare the docs/ folder for GitHub Pages
tasks.register("deployToDocs") {
    group = "deployment"
    dependsOn("wasmJsBrowserDistribution")
    doLast {
        val buildDir = layout.buildDirectory.dir("dist/wasmJs/productionExecutable").get().asFile
        val docsDir = rootProject.layout.projectDirectory.dir("docs").asFile
        
        delete(docsDir)
        copy {
            from(buildDir)
            into(docsDir)
        }
        File(docsDir, ".nojekyll").writeText("")
        println("Production files copied to ${docsDir.absolutePath}")
    }
}

// Task to deploy production files directly to the project root for GitHub Pages
tasks.register("deploy") {
    group = "deployment"
    dependsOn("wasmJsBrowserDistribution")
    doLast {
        val buildDir = layout.buildDirectory.dir("dist/wasmJs/productionExecutable").get().asFile
        val rootDir = rootProject.layout.projectDirectory.asFile
        
        copy {
            from(buildDir)
            into(rootDir)
        }
        File(rootDir, ".nojekyll").writeText("")
        println("Production files deployed to root: ${rootDir.absolutePath}")
    }
}
