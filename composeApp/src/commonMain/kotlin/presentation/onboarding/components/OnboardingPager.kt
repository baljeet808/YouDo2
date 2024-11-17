package presentation.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.OnBoardPagerContent
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.shared.fonts.RobotoFontFamily

@ExperimentalResourceApi
@Composable
fun OnboardingPager(
    pagerContent: OnBoardPagerContent,
    headingColor: Color = Color.White,
    headingFontSize: Int = 18,
    descriptionColor: Color = Color.White,
    descriptionFontSize: Int = 16,
) {

    Column(
        modifier = Modifier.fillMaxWidth()
            .background(
                color = pagerContent.backgroundColor.copy(alpha = 0.7f)
            ).padding(
                top = 50.dp,
                start = 20.dp,
                end = 20.dp,
                bottom = 20.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Image(
            painterResource(pagerContent.res),
            contentDescription = "todo illustration",
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .padding(bottom = 30.dp)
                .background(color = Color.Transparent)
        )

        Text(
            text = pagerContent.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            color = headingColor,
            textAlign = TextAlign.Start,
            fontSize = headingFontSize.sp,
            fontFamily = RobotoFontFamily(),
            fontWeight = FontWeight.Black,
            lineHeight = 50.sp
        )


        Text(
            text = pagerContent.description,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(1f),
            textAlign = TextAlign.Start,
            fontFamily = RobotoFontFamily(),
            fontSize = descriptionFontSize.sp,
            fontWeight = FontWeight.Normal,
            color = descriptionColor,
            lineHeight = 30.sp
        )
    }
}