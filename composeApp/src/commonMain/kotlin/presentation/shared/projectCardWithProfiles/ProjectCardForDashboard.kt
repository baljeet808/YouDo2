package presentation.shared.projectCardWithProfiles

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.getColor
import domain.models.Project
import domain.models.User
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.shared.ProfilesLazyRow
import presentation.shared.fonts.AlataFontFamily
import presentation.shared.projectCardWithProfiles.components.UserImageAndRoleInfoColumn
import presentation.theme.DoTooYellow
import presentation.theme.LessTransparentWhiteColor
import presentation.theme.NightTransparentWhiteColor

@ExperimentalResourceApi
@Composable
fun ProjectCardForDashboard(
    project: Project,
    users : List<User> = emptyList(),
    openProject : () -> Unit = {}
) {


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(20.dp))
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = project.color.getColor())
            .clickable {
                openProject()
            },
        verticalArrangement = Arrangement.SpaceAround
    ) {

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
                color = project.color.getColor(),
                radius = 100.dp.toPx(),
                center = Offset(
                    x = 50.dp.toPx(),
                    y = 100.dp.toPx()
                )
            )

            //creating lines using canvas
            for (i in 1..6) {
                drawLine(
                    color = NightTransparentWhiteColor,
                    strokeWidth = 4.dp.toPx(),
                    start = Offset(
                        x = (170 + (i * 25)).dp.toPx(),
                        y = (0).dp.toPx()
                    ),
                    end = Offset(
                        x = (160).dp.toPx(),
                        y = (10 + (i * 25)).dp.toPx()
                    )
                )
            }
            for (i in 1..8) {
                drawLine(
                    color = NightTransparentWhiteColor,
                    strokeWidth = 4.dp.toPx(),
                    start = Offset(
                        x = (320 + (i * 25)).dp.toPx(),
                        y = (0).dp.toPx()
                    ),
                    end = Offset(
                        x = (135 + (i * 25)).dp.toPx(),
                        y = (185).dp.toPx()
                    )
                )
            }
        })

        if(project.description.isNotBlank()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = project.description,
                    modifier = Modifier
                        .fillMaxWidth()

                        .padding(start = 5.dp, end = 5.dp),
                    color = LessTransparentWhiteColor,
                    fontSize = 16.sp,
                    fontFamily = AlataFontFamily(),
                    letterSpacing = TextUnit(value = 1.5f, TextUnitType.Sp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = project.name,
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                fontFamily = AlataFontFamily(),
                fontSize = 30.sp,
                color = Color.White,
                lineHeight = TextUnit(49f, TextUnitType.Sp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                UserImageAndRoleInfoColumn(
                    adminAvatar = project.ownerAvatarUrl,
                    adminName = project.ownerName,
                    imagesWidthAndHeight = 24
                )

                ProfilesLazyRow(
                    profiles = users,
                    onTapProfiles = {
                        //TODO: show profiles card
                    },
                    visiblePictureCount = 3,
                    imagesWidthAndHeight = 24,
                    spaceBetween = 8,
                    lightColor = DoTooYellow
                )

                Spacer(modifier = Modifier)

                Text(
                    text = project.numberOfTasks.toString().plus(" Tasks"),
                    modifier = Modifier
                        .padding(start = 5.dp, end = 5.dp),
                    color = LessTransparentWhiteColor,
                    fontSize = 12.sp,
                    fontFamily = AlataFontFamily(),
                    letterSpacing = TextUnit(value = 1f, TextUnitType.Sp)
                )
            }
        }
    }
}
