package presentation.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.OnBoardPagerContent
import org.jetbrains.compose.resources.painterResource
import presentation.shared.fonts.CantarellFontFamily

@Composable
fun OnboardingPager(
    pagerContent : OnBoardPagerContent
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = pagerContent.title,
            fontFamily = CantarellFontFamily(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = TextUnit(45F, TextUnitType.Sp),
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Image(
            painterResource(pagerContent.res),
            contentDescription = "todo illustration",
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 270.dp)
                .background(color = Color.Transparent)
        )

        Text(
            text = pagerContent.description,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp),
            textAlign = TextAlign.Center,
            fontFamily = CantarellFontFamily(),
            fontSize = 16.sp,
            lineHeight = TextUnit(30F, TextUnitType.Sp)
        )
    }
}