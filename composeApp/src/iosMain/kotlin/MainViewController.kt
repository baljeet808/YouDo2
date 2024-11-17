import androidx.compose.ui.window.ComposeUIViewController
import di.initKoin
import org.jetbrains.compose.resources.ExperimentalResourceApi

@ExperimentalResourceApi
fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}