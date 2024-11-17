package presentation.onboarding

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import common.getOnboardingPagerContentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.onboarding.components.NextButton
import presentation.onboarding.components.OnboardingPager
import presentation.onboarding.components.PageCountIndicatorView
import presentation.shared.fonts.AlataFontFamily
import presentation.theme.NightTransparentWhiteColor

@ExperimentalResourceApi
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    moveToLogin: () -> Unit = {}
) {

    val list = getOnboardingPagerContentList()
    val pagerState = rememberPagerState(pageCount = { list.count() })
    val coroutineScope = rememberCoroutineScope()


    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxSize()
    ) {
        OnboardingPager(
            pagerContent = list[it],
            headingColor = Color.White,
            headingFontSize = 36,
            descriptionColor = Color.White,
            descriptionFontSize = 18
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        contentAlignment = Alignment.TopEnd
    ) {
        Text(
            text = "Skip",
            color = Color.White,
            modifier = Modifier
                .background(
                    color = NightTransparentWhiteColor,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(10.dp)
                .clickable {
                    moveToLogin()
                },
            fontFamily = AlataFontFamily(),
            fontWeight = FontWeight.Thin
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 40.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            PageCountIndicatorView(
                count = list.count(),
                currentPage = pagerState.currentPage,
                modifier = Modifier.fillMaxWidth(0.7f)
            )

            NextButton(
                backgroundColor = list[(pagerState.currentPage + 1) % 3].backgroundColor,
                contentColor = Color.White,
                onClick = {
                    when (pagerState.currentPage) {
                        list.count() - 1 -> moveToLogin()
                        else -> coroutineScope.launch {
                            delay(100)
                            pagerState.animateScrollToPage(
                                pagerState.currentPage + 1,
                                animationSpec = tween(
                                    durationMillis = 500,
                                    easing = FastOutSlowInEasing
                                )
                            )
                        }
                    }
                }
            )
        }

    }
}