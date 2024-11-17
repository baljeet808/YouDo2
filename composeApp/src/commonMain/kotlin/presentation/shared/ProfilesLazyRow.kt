package presentation.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import common.getRandomAvatar
import domain.models.User
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.shared.fonts.AlataFontFamily
import presentation.theme.DoTooYellow
import presentation.theme.getLightThemeColor

@ExperimentalResourceApi
@Composable
fun ProfilesLazyRow(
    profiles: List<User>,
    onTapProfiles: () -> Unit,
    visiblePictureCount: Int = 3,
    imagesWidthAndHeight: Int = 24,
    spaceBetween: Int = 5,
    lightColor: Color = DoTooYellow
) {

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onTapProfiles),
        contentPadding = PaddingValues(all = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(
            spaceBetween.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {

        val profilesCount = profiles.count()
        items(
            profiles.take(
                if (profilesCount > visiblePictureCount) {
                    visiblePictureCount - 1
                } else visiblePictureCount
            )

        ) { profile ->
            CoilImage(
                imageModel = { profile.avatarUrl.ifEmpty { getRandomAvatar() } },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                ),
                modifier = Modifier
                    .width(imagesWidthAndHeight.dp)
                    .height(imagesWidthAndHeight.dp)
                    .clip(shape = RoundedCornerShape(20.dp))
            )
        }

        if (profilesCount > visiblePictureCount) {
            item {
                Column(
                    modifier = Modifier
                        .width(imagesWidthAndHeight.dp)
                        .height(imagesWidthAndHeight.dp)
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(
                            color = getLightThemeColor()
                        ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = (profiles.size - (visiblePictureCount - 1)).toString().plus("+"),
                        color = if (isSystemInDarkTheme()) {
                            Color.White
                        } else {
                            lightColor
                        },
                        fontSize = 14.sp,
                        fontFamily = AlataFontFamily(),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }

}
