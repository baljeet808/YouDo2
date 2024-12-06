package presentation.project.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
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
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.shared.fonts.AlataFontFamily
import presentation.theme.getDarkThemeColor
import youdo2.composeapp.generated.resources.Res
import youdo2.composeapp.generated.resources.circle_24dp
import youdo2.composeapp.generated.resources.task_alt_24dp

@ExperimentalResourceApi
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskView(
    taskWithProject: TaskWithProject,
    navigateToTaskEdit: () -> Unit,
    navigateToQuickEditTask : () -> Unit,
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
                        navigateToQuickEditTask()
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
                    painter =
                    if (taskWithProject.task.done) {
                        painterResource(resource = Res.drawable.task_alt_24dp)
                    } else {
                        painterResource(resource = Res.drawable.circle_24dp)
                    },
                    contentDescription = "Checked circular icon",
                    tint = if( taskWithProject.task.done) {if(taskWithProject.project.color == 4281347373) { Color.White } else taskWithProject.project.color.getColor() } else Color.Gray,
                    modifier = Modifier
                        .height(if(usingForDemo){ 20.dp }else {30.dp})
                        .width(if(usingForDemo){ 20.dp }else {30.dp})
                )
            }
            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = taskWithProject.task.title,
                color = if(taskWithProject.task.done){ Color.Gray } else Color.White,
                fontFamily = AlataFontFamily(),
                fontSize = if(usingForDemo){ 13.sp }else {18.sp},
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = if (taskWithProject.task.done) {
                    TextStyle(textDecoration = TextDecoration.LineThrough)
                } else {
                    TextStyle()
                },
                modifier = Modifier
                    .weight(if(usingForDemo){ 0.7f }else {0.9f})
            )
        }
    }
}
