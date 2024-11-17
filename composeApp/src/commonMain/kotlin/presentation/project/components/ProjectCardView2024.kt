package presentation.project.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil3.CoilImage
import common.getColor
import common.getRandomAvatar
import domain.models.Project
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.shared.fonts.AlataFontFamily
import youdo2.composeapp.generated.resources.Res
import youdo2.composeapp.generated.resources.app_icon
import youdo2.composeapp.generated.resources.baseline_arrow_outward_24
import youdo2.composeapp.generated.resources.baseline_castle_24
import youdo2.composeapp.generated.resources.baseline_link_18
import youdo2.composeapp.generated.resources.baseline_visibility_24
import youdo2.composeapp.generated.resources.omg

@ExperimentalResourceApi
@Composable
fun ProjectCardView2024(
    project: Project
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = project.color.getColor(),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(10.dp),
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            // Top Header with brand and icons
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        painter = painterResource(Res.drawable.baseline_castle_24),
                        contentDescription = "View Icon",
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "Karandeep Kaur",
                        fontFamily = AlataFontFamily(),
                        fontSize = 16.sp
                    )
                }

                Row {
                    Icon(
                        painter = painterResource(Res.drawable.baseline_visibility_24),
                        contentDescription = "View Icon",
                        modifier = Modifier
                            .border(width = 1.dp, color = Color.LightGray, shape = CircleShape)
                            .padding(8.dp)
                            .size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(Res.drawable.baseline_arrow_outward_24),
                        tint = Color.White,
                        contentDescription = "External Link Icon",
                        modifier = Modifier
                            .background(color = Color.Black, shape = CircleShape)
                            .padding(8.dp)
                            .size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Title Section
            Text(
                text = "Development",
                color = Color.Black,
                fontFamily = AlataFontFamily(),
                fontSize = 16.sp
            )
            Text(
                text = project.name,
                fontWeight = FontWeight.Bold,
                fontFamily = AlataFontFamily(),
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            // "Read now" Button with Chevron
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().clickable { /* Handle Read Now Click */ },
                horizontalArrangement = Arrangement.Absolute.SpaceBetween
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ){
                    // Profile Faces (Replace with actual images)
                    Image(
                        painter = painterResource(Res.drawable.omg),
                        contentDescription = "User 1",
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                    )
                    Image(
                        painter = painterResource(Res.drawable.omg),
                        contentDescription = "User 2",
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                    )
                    Image(
                        painter = painterResource(Res.drawable.omg),
                        contentDescription = "User 3",
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                    )
                }

                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {

                    Text(
                        text = "Join now",
                        color = Color.Gray,
                        fontFamily = AlataFontFamily(),
                        fontSize = 14.sp,
                        textDecoration = TextDecoration.Underline
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Read Now Icon",
                        tint = Color.White,
                        modifier = Modifier
                            .background(color = Color.LightGray, shape = CircleShape)
                            .padding(2.dp)
                            .size(14.dp)
                    )
                }
            }


            Spacer(modifier = Modifier.height(8.dp))

            // Image with Engagement Row
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                CoilImage(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(
                            shape = RoundedCornerShape(
                                topEnd = 40.dp,
                                topStart = 40.dp,
                                bottomStart = 40.dp,
                                bottomEnd = 0.dp
                            )
                        ),
                    imageModel = { "https://images.unsplash.com/photo-1515879218367-8466d910aaa4?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8Y29kZXxlbnwwfHwwfHx8MA%3D%3D".ifEmpty { getRandomAvatar() } },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center,
                    ),
                    loading = {
                        Box(
                            modifier = Modifier .fillMaxSize()
                                .clip(
                                    shape = RoundedCornerShape(
                                        topEnd = 40.dp,
                                        topStart = 40.dp,
                                        bottomStart = 40.dp,
                                        bottomEnd = 0.dp
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        }
                    },
                    failure = {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(
                                    shape = RoundedCornerShape(
                                        topEnd = 40.dp,
                                        topStart = 40.dp,
                                        bottomStart = 40.dp,
                                        bottomEnd = 0.dp
                                    )
                                )
                                .background(Color.Gray.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painterResource(Res.drawable.app_icon),
                                contentDescription = "avatarImage",
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                        }
                    }

                )

                // Floating URL Badge
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(20.dp)
                        .background(Color.White, RoundedCornerShape(40.dp))
                        .padding(horizontal = 15.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "StackOverflow.com",
                        fontFamily = AlataFontFamily(),
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        painter = painterResource(Res.drawable.baseline_link_18),
                        tint = Color.Magenta,
                        contentDescription = "Link Icon",
                        modifier = Modifier.size(16.dp)
                    )
                }

                // Engagement Section (Likes, Faces, etc.)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(20.dp)
                        .background(Color.White, RoundedCornerShape(40.dp))
                        .padding(horizontal = 15.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Likes",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "1.4k",
                        modifier = Modifier.padding(start = 4.dp)
                    )

                }
            }
        }
    }
}