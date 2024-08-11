package presentation.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import common.getOnBoardPagerContentList
import presentation.onboarding.components.OnboardingPager
import presentation.shared.HoverButton

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    moveToLogin: () -> Unit = {}
) {

    //pager related setup
    val list = getOnBoardPagerContentList()
    val pagerState = rememberPagerState(pageCount = { list.count() })

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        /**
         *On boarding screens
         * **/
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
        ) {
            OnboardingPager(pagerContent = list[it])
        }
    }

    AnimatedVisibility(
        visible = pagerState.currentPage != list.count() - 1,
        enter = fadeIn(animationSpec = tween(200)), exit = fadeOut(animationSpec = tween(200))
    ){
        HoverButton(
            onClick = {
                moveToLogin()
            },
            buttonLabel = "Skip",
            contentAlignment = Alignment.BottomStart
        )
    }
    AnimatedVisibility(
        visible = pagerState.currentPage == list.count() - 1,
        enter = fadeIn(animationSpec = tween(200)), exit = fadeOut(animationSpec = tween(200))
    ){
        HoverButton(
            onClick = {
                moveToLogin()
            },
            buttonLabel = "Get Started",
            contentAlignment = Alignment.BottomEnd
        )
    }
}