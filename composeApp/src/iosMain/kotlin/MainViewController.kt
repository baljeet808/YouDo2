import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import common.createDatastore
import di.initKoin
import navigation.RootComponent

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    val preferences = createDatastore()
    val root = remember {
        RootComponent(
            componentContext = DefaultComponentContext(LifecycleRegistry())
        )
    }
    App(
        root = root,
        prefs = preferences
    )
}