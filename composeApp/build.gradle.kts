import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    //Room
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    //firebase
    alias(libs.plugins.googleServices)
    //navigation
    alias(libs.plugins.kotlinSerialization)
}

kotlin {


    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)


            //Koin
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)

            //Splashscreen
            implementation(libs.core.splashscreen)

            //firebase-bom
            implementation(project.dependencies.platform(libs.android.firebase.bom))
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.firebase.auth)
            implementation(libs.firebase.firestore)
            implementation(libs.firebase.analytics)



            //Room
            implementation(libs.androidx.room.runtime)
            implementation(libs.sqlite.bundled)

            //Koin
            api(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.lifecycle.viewmodel)
            implementation(libs.navigation.compose)

            //firebase-auth
            implementation(libs.firebase.auth)

            //DataStore
            api(libs.datastore.preferences)
            api(libs.datastore)

            //datetime
            implementation(libs.kotlinx.datetime)

            //coil
            implementation(libs.landscapist.coil3)
        }
    }
    sourceSets.commonMain {
        kotlin.srcDir("build/generated/ksp/metadata")
    }
}

android {
    namespace = "com.baljeet.youdo2"
    compileSdkVersion(libs.versions.android.compileSdk.get().toInt())

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
    sourceSets["main"].kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")

    defaultConfig {
        applicationId = "com.baljeet.youdo2"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}


tasks.named("kspDebugKotlinAndroid").configure {
    dependsOn(tasks.named("kspCommonMainKotlinMetadata"))
}

tasks.named("kspDebugAndroidTestKotlinAndroid").configure {
    dependsOn(tasks.named("kspCommonMainKotlinMetadata"))
}

tasks.named("kspKotlinIosArm64").configure {
    dependsOn(tasks.named("kspCommonMainKotlinMetadata"))
}

tasks.named("kspKotlinIosSimulatorArm64").configure {
    dependsOn(tasks.named("kspCommonMainKotlinMetadata"))
}

tasks.named("kspKotlinIosX64").configure {
    dependsOn(tasks.named("kspCommonMainKotlinMetadata"))
}

tasks.named("kspReleaseKotlinAndroid").configure {
    dependsOn(tasks.named("kspCommonMainKotlinMetadata"))
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies{
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.places)
    ksp(libs.androidx.room.compiler)
}
