package com.baljeet.youdo2

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.FirebaseApp
import org.jetbrains.compose.resources.ExperimentalResourceApi

@ExperimentalResourceApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        FirebaseApp.initializeApp(this)
        setContent {
            App()
        }
    }
}
