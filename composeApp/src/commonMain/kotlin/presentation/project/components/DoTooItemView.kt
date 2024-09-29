package presentation.project.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.getColor
import domain.models.TaskWithProject
import presentation.shared.fonts.AlataFontFamily
import presentation.theme.getDarkThemeColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DoTooItemView(
    doToo: TaskWithProject,
    navigateToTaskEdit: () -> Unit,
    navigateToQuickEditDotoo : () -> Unit,
    onToggleDone: () -> Unit,
    usingForDemo : Boolean = false
) {


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(if(usingForDemo){ 50.dp }else {80.dp})
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(if(usingForDemo){ 14.dp }else {20.dp}))
            .background(
                color = getDarkThemeColor(),
                shape = RoundedCornerShape(if(usingForDemo){ 14.dp }else {20.dp})
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .combinedClickable(
                    onClick = navigateToTaskEdit,
                    onLongClick = {
                        navigateToQuickEditDotoo()
                    }
                )
                .padding(
                    start = if(usingForDemo){ 6.dp }else {10.dp},
                    end = 0.dp,
                    top = if(usingForDemo){ 6.dp }else {10.dp},
                    bottom = if(usingForDemo){ 6.dp }else {10.dp}
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {


            IconButton(
                onClick = {
                    onToggleDone()
                },
                modifier = Modifier
                    .height(if(usingForDemo){ 20.dp }else {30.dp})
                    .width(if(usingForDemo){ 20.dp }else {30.dp})
                    .padding(0.dp),
            ) {
                Icon(
                    if (doToo.task.done) {
                        Icons.Filled.CheckCircle
                    } else {
                        Icons.Outlined.CheckCircle
                    },
                    contentDescription = "Checked circular icon",
                    tint = doToo.project.color.getColor(),
                    modifier = Modifier
                        .height(if(usingForDemo){ 20.dp }else {30.dp})
                        .width(if(usingForDemo){ 20.dp }else {30.dp})
                )
            }
            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = doToo.task.title,
                color = if (isSystemInDarkTheme()) {
                    Color.White
                } else {
                    Color.Black
                },
                fontFamily = AlataFontFamily(),
                fontSize = if(usingForDemo){ 13.sp }else {18.sp},
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = if (doToo.task.done) {
                    TextStyle(textDecoration = TextDecoration.LineThrough)
                } else {
                    TextStyle()
                },
                modifier = Modifier
                    .weight(if(usingForDemo){ 0.7f }else {0.9f})
            )

            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription ="Navigate to chat button",
                tint = doToo.project.color.getColor(),
                modifier = Modifier
                    .height(if(usingForDemo){ 20.dp }else {30.dp})
                    .width(if(usingForDemo){ 20.dp }else {30.dp})
            )
        }
    }
}
