package epm.xnox.fruitshop.presentation.home.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import epm.xnox.fruitshop.domain.model.Fruit
import epm.xnox.fruitshop.presentation.home.viewModel.HomeEvent
import epm.xnox.fruitshop.presentation.home.viewModel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: HomeViewModel,
    onNavigate: () -> Unit,
    onNavigateItem: (item: Fruit) -> Unit
) {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(true) }
    val items = remember { mutableStateListOf<String>() }
    val state = viewModel.state.value

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            query = text,
            active = active,
            onQueryChange = {
                text = it
            },
            onSearch = {
                items.add(text)
                viewModel.onEvent(HomeEvent.OnSearch(text))
                active = false
            },
            onActiveChange = {
                active = it
            },
            placeholder = {
                Text(text = "Buscar fruta")
            },
            leadingIcon = {
                IconButton(onClick = { onNavigate() }) {
                    Icon(
                        imageVector = Icons.Outlined.ArrowBack,
                        contentDescription = "Icon back"
                    )
                }
            },
            trailingIcon = {
                if (active) {
                    IconButton(onClick = {
                        if (text.isNotEmpty())
                            text = ""
                        else
                            active = false
                    }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Icon clear"
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEach {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                        .clickable { text = it }
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Icon recent search"
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = it)
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        LazyVerticalGrid(
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp, start = 5.dp, end = 5.dp),
            columns = GridCells.Fixed(2),
            content = {
                items(state.searchResult) { item ->
                    FruitItem(item = item) { fruit ->
                        onNavigateItem(fruit)
                    }
                }
            }
        )
    }
}