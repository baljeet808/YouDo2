package com.baljeet.youdo2

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.retainedComponent
import common.createDatastore
import navigation.RootComponent

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalDecomposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val preferences = remember {
                createDatastore(applicationContext)
            }
            val root = retainedComponent {
                RootComponent(
                    componentContext = it,
                    preferences = preferences
                )
            }
            App(
                root = root,
                prefs = preferences
            )
        }
    }
}
