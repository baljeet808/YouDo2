import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App(
    prefs: DataStore<Preferences>
) {
    MaterialTheme {

    }
}