package presentation.chat.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.coil3.CoilImage
import common.getInteractions
import common.toNiceDateTimeFormat
import data.local.entities.MessageEntity
import data.local.entities.UserEntity
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.shared.fonts.AlataFontFamily
import presentation.theme.DoTooLightBlue
import presentation.theme.DotooBlue


@ExperimentalResourceApi
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ThereMessageBubbleView(
    message: MessageEntity,
    users : List<UserEntity>,
    onLongPress: () -> Unit,
    showSenderInfo: Boolean = true
) {

    val screenWidthInDp = 300

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onLongClick = onLongPress,
                onClick = {}
            )
            .padding(start = 10.dp, end = 10.dp, top = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
        verticalAlignment = Alignment.Top
    ) {


        if (showSenderInfo) {
            Box(
                modifier = Modifier
                    .width(35.dp)
                    .height(35.dp)
                    .border(
                        width = 2.dp,
                        color = if (isSystemInDarkTheme()) {
                            DoTooLightBlue
                        } else {
                            DotooBlue
                        },
                        shape = RoundedCornerShape(40.dp)
                    )
                    .padding(3.dp)
            ) {
                CoilImage(
                    imageModel = { users.getUserProfilePicture(message.senderId) },
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp)
                        .clip(shape = RoundedCornerShape(40.dp))

                )
            }

        } else {
            Spacer(
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
            )
        }

        Column(
            modifier = Modifier
                .widthIn(min = 60.dp, max = (screenWidthInDp/1.4).dp),
            verticalArrangement = Arrangement.spacedBy(0.dp, alignment = Alignment.Top),
            horizontalAlignment = Alignment.Start
        ) {
            if (showSenderInfo) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 3.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp, alignment = Alignment.Start)
                ) {

                    Text(
                        text = users.getUserName(message.senderId)?:"Unknown",
                        fontFamily = AlataFontFamily(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                    )

                    Text(
                        text = message.createdAt.toNiceDateTimeFormat(true),
                        fontFamily = AlataFontFamily(),
                        fontSize = 11.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .padding(top = 4.dp)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .widthIn(min = 60.dp, max = (screenWidthInDp/1.4).dp)
                    .background(
                        color = DoTooLightBlue,
                        shape = RoundedCornerShape(
                            topEnd = 20.dp,
                            topStart = 0.dp,
                            bottomStart = 20.dp,
                            bottomEnd = 20.dp
                        )
                    )
                    .padding(10.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
               /* message.attachmentUrl?.let { url ->
                    AsyncImage(
                        model = url,
                        contentDescription = "Attachment image",
                        modifier = Modifier
                            .height((screenWidthInDp/1.4).dp)
                            .clip(shape = RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Crop,
                    )
                }*/
                Text(
                    text = message.message,
                    fontFamily = AlataFontFamily(),
                    fontSize = 14.sp,
                    color = DotooBlue
                )
            }
            if (message.interactions.isNotEmpty()) {
                EmoticonsSmallPreview(
                    interactions = message.interactions.getInteractions(),
                    onViewClicked = onLongPress
                )
            }
        }


    }
}
