package presentation.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import presentation.shared.fonts.ReenieBeanieFontFamily

@OptIn(KoinExperimentalAPI::class)
@Composable
fun DashboardScreen(
    navigateToLogin: () -> Unit,
    prefs : DataStore<Preferences>
) {

    val dashboardViewModel = koinViewModel<DashboardViewModel>()
    val uiState = dashboardViewModel.uiState

    val scope = rememberCoroutineScope()


    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ){

        Text(
            text = uiState.userEmail,
            fontFamily = ReenieBeanieFontFamily(),
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp
        )

        Button(
            onClick = {

            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ){
            Text("Logout")
        }
    }

}