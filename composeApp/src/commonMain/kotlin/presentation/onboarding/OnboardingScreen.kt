package presentation.onboarding

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import common.getOnBoardPagerContentList
import kotlinx.coroutines.launch
import presentation.onboarding.components.OnboardingPager
import presentation.onboarding.components.PageCountIndicatorView
import presentation.shared.fonts.AlataFontFamily
import presentation.theme.NightTransparentWhiteColor
import presentation.theme.getNightDarkColor
import presentation.theme.getNightLightColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    moveToLogin: () -> Unit = {}
) {

    //pager related setup
    val list = getOnBoardPagerContentList()
    val pagerState = rememberPagerState(pageCount = { list.count() })
    val coroutineScope = rememberCoroutineScope()
    Box (
        modifier = Modifier.fillMaxSize()
            .background(
                color = if (isSystemInDarkTheme()) {
                    getNightDarkColor()
                } else {
                    getNightLightColor()
                }
            )
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            Canvas(modifier = Modifier.fillMaxWidth(), onDraw = {
                drawCircle(
                    color = NightTransparentWhiteColor,
                    radius = 230.dp.toPx(),
                    center = Offset(
                        x = 40.dp.toPx(),
                        y = 100.dp.toPx()
                    )
                )
                drawCircle(
                    color = Color.White.copy(alpha = 0.7F),
                    radius = 100.dp.toPx(),
                    center = Offset(
                        x = 20.dp.toPx(),
                        y = 100.dp.toPx()
                    )
                )



                drawCircle(
                    color = NightTransparentWhiteColor,
                    radius = 230.dp.toPx(),
                    center = Offset(
                        x = 240.dp.toPx(),
                        y = 550.dp.toPx()
                    )
                )

            })
        }
        /**
         *On boarding screens
         * **/
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
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        contentAlignment = Alignment.TopEnd
    ){
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
    ){
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

            Row(
                modifier = Modifier
                    .width(80.dp)
                    .height(60.dp)
                    .background(
                        color = list[(pagerState.currentPage +1)%3 ].backgroundColor,
                        shape = RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp)
                    )
                    .clickable {
                        if(pagerState.currentPage == list.count() - 1){
                            moveToLogin()
                        }else{
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    }
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            )
            {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Next",
                    tint = Color.White
                )
            }
        }

    }
}