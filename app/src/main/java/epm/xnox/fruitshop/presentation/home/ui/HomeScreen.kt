package epm.xnox.fruitshop.presentation.home.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import epm.xnox.fruitshop.R
import epm.xnox.fruitshop.domain.model.Fruit
import epm.xnox.fruitshop.presentation.home.viewModel.HomeEvent
import epm.xnox.fruitshop.presentation.home.viewModel.HomeState
import epm.xnox.fruitshop.presentation.home.viewModel.HomeViewModel
import epm.xnox.fruitshop.ui.navigation.NavRoutes
import epm.xnox.fruitshop.ui.theme.BannerDark
import epm.xnox.fruitshop.ui.theme.BannerLight
import epm.xnox.fruitshop.ui.theme.Orange60

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNavigateItem: (item: Fruit) -> Unit,
    onNavigateBottomBar: (route: NavRoutes) -> Unit
) {
    val state = viewModel.state.value

    Scaffold(
        bottomBar = {
            BottomNavigation(onNavigateBottomBar)
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Header()
            Banner()
            Category(viewModel)

            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
                }
            } else {
                if (state.error.isNotBlank()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Ocurrió un error",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = state.error,
                            fontWeight = FontWeight.Light
                        )
                    }
                } else {
                    Content(onNavigateItem, state)
                }
            }
        }
    }
}

@Composable
fun Header() {
    var showMenu by remember { mutableStateOf(false) }
    val menuOptions = mapOf("Ajustes" to Icons.Outlined.Settings)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Fruit Shop",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = { showMenu = true }) {
            Icon(
                painter = painterResource(id = R.drawable.segment),
                contentDescription = "Home menu"
            )
            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                menuOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(text = option.key) },
                        leadingIcon = {
                            Icon(
                                imageVector = option.value,
                                contentDescription = null
                            )
                        },
                        onClick = {
                            showMenu = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Banner() {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .height(120.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_banner_background),
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .alpha(0.7f)
                .background(
                    color = if (isSystemInDarkTheme()) BannerDark else BannerLight,
                    shape = RoundedCornerShape(16.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "30%\nOFF",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                modifier = Modifier.padding(start = 10.dp)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp)
            ) {
                Text(
                    text = "Oferta especial por hoy",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Realiza una compra de mas de $20.00 y el envio sera gratuito.",
                    fontWeight = FontWeight.Light,
                    color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                )
            }
        }
    }
}

@Composable
fun Category(viewModel: HomeViewModel) {
    var categoryState: CategoryType by remember { mutableStateOf(CategoryType.Todo) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if (categoryState == CategoryType.Todo)
                        MaterialTheme.colorScheme.secondary
                    else
                        Color.Transparent,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable {
                    if (categoryState != CategoryType.Todo) {
                        categoryState = CategoryType.Todo
                        viewModel.onEvent(
                            HomeEvent.GetFruits(
                                CategoryType.Todo
                            )
                        )
                    }
                }
        ) {
            Text(text = "Todo", fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp))
        }
        Spacer(modifier = Modifier.width(15.dp))
        Box(
            modifier = Modifier
                .background(
                    color = if (categoryState == CategoryType.Nuevo) MaterialTheme.colorScheme.secondary else Color.Transparent,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable {
                    if (categoryState != CategoryType.Nuevo) {
                        categoryState = CategoryType.Nuevo
                        viewModel.onEvent(
                            HomeEvent.GetFruits(
                                CategoryType.Nuevo
                            )
                        )
                    }
                }
        ) {
            Text(text = "Nuevo", fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp))
        }
        Spacer(modifier = Modifier.width(15.dp))
        Box(
            modifier = Modifier
                .background(
                    color = if (categoryState == CategoryType.Popular) MaterialTheme.colorScheme.secondary else Color.Transparent,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable {
                    if (categoryState != CategoryType.Popular) {
                        categoryState = CategoryType.Popular
                        viewModel.onEvent(
                            HomeEvent.GetFruits(
                                CategoryType.Popular
                            )
                        )
                    }
                }
        ) {
            Text(text = "Popular", fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp))
        }
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier.weight(1f)
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.tune),
                    contentDescription = "Icon filter search"
                )
            }
        }
    }
}

@Composable
fun FruitItem(item: Fruit, onNavigateItem: (item: Fruit) -> Unit) {
    Column(
        modifier = Modifier
            .padding(5.dp)
            .height(200.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable {
                onNavigateItem(item)
            }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(8.dp)
                .height(120.dp)
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.image)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.image),
                error = painterResource(id = R.drawable.image),
                contentDescription = "",
                modifier = Modifier.fillMaxSize()
            )
        }
        Text(
            text = item.name,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 8.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$${item.price}",
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = "Icon rating",
                tint = Orange60
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = "${item.rating}")
        }
    }
}

@Composable
fun Content(onNavigateItem: (item: Fruit) -> Unit, state: HomeState) {
    LazyVerticalGrid(
        modifier = Modifier.padding(start = 5.dp, end = 5.dp),
        columns = GridCells.Fixed(2),
        content = {
            items(state.data!!) { item ->
                FruitItem(item, onNavigateItem)
            }
        }
    )
}

@Composable
fun BottomNavigation(onNavigateBottomBar: (route: NavRoutes) -> Unit) {
    var selected by remember { mutableStateOf(MenuItem.Home) }

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.secondary,
        modifier = Modifier
            .height(90.dp)
            .padding(15.dp)
            .clip(
                RoundedCornerShape(16.dp)
            )
    ) {
        NavigationBarItem(
            selected = selected == MenuItem.Home,
            onClick = {
                if (selected != MenuItem.Home) {
                    selected = MenuItem.Home
                    onNavigateBottomBar(NavRoutes.HomeScreen)
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = "Icon home"
                )
            }
        )
        NavigationBarItem(
            selected = selected == MenuItem.Cart,
            onClick = {
                if (selected != MenuItem.Cart) {
                    selected = MenuItem.Cart
                    onNavigateBottomBar(NavRoutes.CartScreen)
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = "Icon cart"
                )
            }
        )
        NavigationBarItem(
            selected = selected == MenuItem.Search,
            onClick = {
                if (selected != MenuItem.Search) {
                    selected = MenuItem.Search
                    onNavigateBottomBar(NavRoutes.SearchScreen)
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = "Icon search"
                )
            }
        )
        NavigationBarItem(
            selected = selected == MenuItem.Account,
            onClick = {
                if (selected != MenuItem.Account) {
                    selected = MenuItem.Account
                    onNavigateBottomBar(NavRoutes.AccountScreen)
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.AccountCircle,
                    contentDescription = "Icon account"
                )
            }
        )
    }
}

enum class CategoryType {
    Todo, Nuevo, Popular
}

enum class MenuItem {
    Home, Cart, Account, Search
}

