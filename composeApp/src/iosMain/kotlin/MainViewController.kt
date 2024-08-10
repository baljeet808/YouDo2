import androidx.compose.ui.window.ComposeUIViewController
import common.createDatastore
import di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    val preferences = createDatastore()
    App(
        prefs = preferences
    )
}