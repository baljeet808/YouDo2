package presentation.dashboard.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import common.getColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBoxView(
    onSearch: (query: String) -> Unit = {},
    onClearSearch: () -> Unit = {},
) {

    var active by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    SearchBar(
        colors = SearchBarDefaults.colors(
            containerColor = Color.Gray.copy(alpha = 0.3f),
        ),
        modifier =  Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .padding(10.dp),
        query = searchText,
        onQueryChange = {
            searchText = it
        },
        onSearch = {
            active = false
            onSearch(searchText)
        },
        active = active,
        onActiveChange = {
            active = it
        },
        placeholder = {
            Text(
                text = "Search task, projects ",
                color = Color.White.copy(alpha = 0.7f)
            )
        },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color.White.copy(alpha = 0.7f)
            )
        },
        trailingIcon = {
            if (active || searchText.isNotEmpty()) {
                Icon(
                    modifier = Modifier
                        .clickable {
                            if (searchText.isNotEmpty()) {
                                searchText = ""
                                onClearSearch()
                            } else {
                                active = false
                            }
                        },
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Icon",
                    tint = Color.White.copy(alpha = 0.7f)
                )
            }
        },
        shape = SearchBarDefaults.dockedShape
    ){

    }
}