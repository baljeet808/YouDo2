package presentation.drawer.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import domain.models.MenuItem
import presentation.shared.fonts.RobotoFontFamily
import presentation.theme.LessTransparentWhiteColor

@Composable
   fun MenuItemRow(menuItem: MenuItem, onMenuItemClick: (MenuItem) -> Unit) {
       Row(
           modifier = Modifier
               .fillMaxWidth()
               .padding(20.dp)
               .clickable(onClick = { onMenuItemClick(menuItem) }),
           horizontalArrangement = Arrangement.Start,
           verticalAlignment = Alignment.CenterVertically
       ) {

           Icon(
               menuItem.icon,
               contentDescription = menuItem.contentDescription,
               tint = LessTransparentWhiteColor
           )

           Spacer(modifier = Modifier.width(10.dp))

           Text(
               text = menuItem.title,
               fontFamily = RobotoFontFamily(),
               color = Color.White,
           )
       }
   }

