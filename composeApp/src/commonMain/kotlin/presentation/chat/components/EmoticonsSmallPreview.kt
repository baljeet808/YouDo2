package presentation.chat.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import common.getDrawableIdByFileName
import domain.models.Interaction
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.shared.fonts.AlataFontFamily
import presentation.theme.getTextColor

@ExperimentalResourceApi
@Composable
fun EmoticonsSmallPreview(
    interactions : ArrayList<Interaction>,
    onViewClicked : () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onViewClicked),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        if(interactions.size>3) {
            Text(
                text = interactions.size.toString(),
                fontFamily = AlataFontFamily(),
                fontSize = 11.sp,
                color = getTextColor()
            )
        }
        Spacer(modifier = Modifier.width(2.dp))
        interactions.take(3).forEach {interaction->
            val drawableId = remember(interaction.emoticonName) {
                getDrawableIdByFileName(interaction.emoticonName)
            }
            Image(
                painter = painterResource(drawableId),
                contentDescription = "Emoticon" ,
                modifier = Modifier
                    .height(16.dp)
                    .width(16.dp)
                    .background(shape = RoundedCornerShape(20.dp), color = Color.Transparent)
            )
        }
    }

}