package presentation.shared.userManager

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import common.COLOR_GRAPHITE_VALUE
import common.COLOR_NIGHT_BLACK_VALUE
import common.getColor
import common.getRandomAvatar
import common.getRole
import data.local.mappers.toProjectEntity
import domain.models.Project
import domain.models.getAllIds
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import presentation.shared.TopHeadingWithCloseButton
import presentation.shared.editboxs.EditOnFlyBox
import presentation.shared.fonts.AlataFontFamily
import presentation.shared.userManager.helper.ProjectUsersManagerScreenEvent
import presentation.shared.userManager.helper.ProjectUsersManagerViewModel
import youdo2.composeapp.generated.resources.Res
import youdo2.composeapp.generated.resources.circle_24dp
import youdo2.composeapp.generated.resources.task_alt_24dp

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ProjectUsersManager(
    project: Project,
    onClose : () -> Unit
) {

    val viewmodel = koinViewModel<ProjectUsersManagerViewModel>()

    val uiState = viewmodel.uiState

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 10.dp, end = 10.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = Color(COLOR_GRAPHITE_VALUE).copy(alpha = 0.5f)),
        horizontalAlignment = Alignment.Start,
    ) {

        TopHeadingWithCloseButton(
            heading = "Do it together",
            onClose = {
                onClose()
            },
            modifier = Modifier,
            smallerText = true
        )

        AnimatedVisibility(visible = uiState.showRecent){
            Column {
                Text(
                    text = "Collaborators",
                    modifier = Modifier.padding(10.dp),
                    color = Color.Gray,
                    fontFamily = AlataFontFamily(),
                    fontSize = 14.sp
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                        .background(
                            color =Color(COLOR_GRAPHITE_VALUE).copy(alpha = 0.5f),
                            shape = RoundedCornerShape(20.dp)
                        )
                ) {
                    item {
                        Text(
                            text = "Recent",
                            modifier = Modifier.weight(.5f).padding(10.dp),
                            color = Color.White,
                            fontFamily = AlataFontFamily(),
                            fontSize = 14.sp,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    items(items = uiState.collaborators){ user ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            val role = getRole(project.toProjectEntity(), user.id)
                            IconButton(
                                onClick = {
                                    viewmodel.onEvent(
                                        ProjectUsersManagerScreenEvent.OnRecentUserCheckChanged(
                                            user = user,
                                            isChecked = project.getAllIds().contains(user.id)
                                        )
                                    )
                                },
                                modifier = Modifier
                                    .height(20.dp)
                                    .width(20.dp)
                                    .padding(0.dp),
                            ) {
                                Icon(
                                    painter =
                                    if (project.getAllIds().contains(user.id)) {
                                        painterResource(resource = Res.drawable.task_alt_24dp)
                                    } else {
                                        painterResource(resource = Res.drawable.circle_24dp)
                                    },
                                    contentDescription = "Checked circular icon",
                                    tint = if(true) {if(project.color == 4281347373) { Color.White } else project.color.getColor() } else Color.Gray,
                                    modifier = Modifier
                                        .height(20.dp)
                                        .width(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            CoilImage(
                                imageModel = { user.avatarUrl.ifEmpty { getRandomAvatar() } },
                                imageOptions = ImageOptions(
                                    contentScale = ContentScale.Crop,
                                    alignment = Alignment.Center,
                                ),
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(shape = RoundedCornerShape(20.dp))
                            )
                            Text(
                                text = user.name,
                                modifier = Modifier.weight(.5f).padding(5.dp),
                                color = Color.White,
                                fontFamily = AlataFontFamily(),
                                fontSize = 14.sp,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = role.name,
                                modifier = Modifier
                                    .background(
                                        color = Color(COLOR_NIGHT_BLACK_VALUE),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .weight(.5f)
                                    .padding(5.dp)
                                    .clickable {

                                    }
                                ,
                                color = Color.Gray,
                                fontFamily = AlataFontFamily(),
                                fontSize = 14.sp,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }

        AnimatedVisibility(visible = true){
            Column {
                Text(
                    text = "Add New",
                    modifier = Modifier.padding(10.dp),
                    color = Color.Gray,
                    fontFamily = AlataFontFamily(),
                    fontSize = 14.sp
                )
                EditOnFlyBox(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                    label = "Sharing Code",
                    placeholder = "",
                    maxCharLength = 6,
                    onCancel = {
                        viewmodel.onEvent(ProjectUsersManagerScreenEvent.OnClickedDismissAddNewBox)
                    },
                    onSubmit = { code ->
                        viewmodel.onEvent(ProjectUsersManagerScreenEvent.OnClickedSubmitCodeButton(sharingCode = code))
                    },
                    themeColor = Color(COLOR_GRAPHITE_VALUE),
                    lines = 1
                )
            }
        }
    }

    LaunchedEffect(key1 = Unit){
        viewmodel.getUsers(project)
    }

}