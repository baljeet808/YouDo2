package presentation.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import presentation.shared.fonts.ReenieBeanieFontFamily

@Composable
fun DashboardScreen(
    component: DashboardComponent
) {
    Column (
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ){

        Text(
            text = "Dashboard",
            fontFamily = ReenieBeanieFontFamily(),
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp
        )

        Button(
            onClick = {
                component.onEvent(DashboardEvents.Logout)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ){
            Text("Logout")
        }
    }

}