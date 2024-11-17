package presentation.chat.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import common.Emoticon
import common.getDrawableIdByFileName
import data.local.entities.MessageEntity
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@ExperimentalResourceApi
@Composable
fun EmoticonsControllerView(
    onItemSelected : (emoticonName : String, message : MessageEntity) -> Unit,
    message: MessageEntity
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 50.dp),
            modifier = Modifier.fillMaxWidth().padding(2.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ){
            items(Emoticon.entries){ emoticon ->
                val drawableId = remember(emoticon) {
                    getDrawableIdByFileName(emoticon.fileName)
                }
                Image(
                    painter = painterResource(drawableId),
                    contentDescription = "Emoticon" ,
                    modifier = Modifier
                        .height(42.dp)
                        .width(42.dp)
                        .clickable {
                            onItemSelected(emoticon.fileName, message)
                        }
                        .background(shape = RoundedCornerShape(20.dp), color = Color.Transparent)
                )
            }
        }
    }
}