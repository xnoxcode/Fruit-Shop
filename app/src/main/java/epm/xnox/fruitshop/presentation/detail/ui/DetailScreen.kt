package epm.xnox.fruitshop.presentation.detail.ui

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import coil.compose.AsyncImage
import coil.request.ImageRequest
import epm.xnox.fruitshop.R
import epm.xnox.fruitshop.presentation.detail.viewModel.DetailEvent
import epm.xnox.fruitshop.presentation.detail.viewModel.DetailViewModel
import epm.xnox.fruitshop.ui.theme.MercuryDark60
import epm.xnox.fruitshop.ui.theme.Orange60

@Composable
fun DetailScreen(
    backStackEntry: NavBackStackEntry,
    viewModel: DetailViewModel,
    onNavigate: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Header(onNavigate, backStackEntry)
        Content(backStackEntry, viewModel)
    }
}

@Composable
fun Header(onNavigate: () -> Unit, backStackEntry: NavBackStackEntry) {
    val fruitImage = backStackEntry.arguments?.getString("image")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(bottomStart = 22.dp, bottomEnd = 22.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, top = 15.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onNavigate() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Icon back"
                )
            }
            Spacer(modifier = Modifier.width(15.dp))
            Text(
                text = "Detalles",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
        }
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(fruitImage)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.image),
                error = painterResource(id = R.drawable.image),
                contentDescription = "",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun Content(backStackEntry: NavBackStackEntry, viewModel: DetailViewModel) {
    val state = viewModel.state.value
    val fruitName = backStackEntry.arguments?.getString("name")
    val fruitDetail = backStackEntry.arguments?.getString("detail")
    val fruitImage = backStackEntry.arguments?.getString("image")
    val fruitPrice = backStackEntry.arguments?.getFloat("price")
    val fruitRating = backStackEntry.arguments?.getFloat("rating")
    var count by remember { mutableStateOf(1) }
    var cost by remember { mutableStateOf(fruitPrice) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 15.dp, start = 10.dp, end = 10.dp, bottom = 15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = fruitName ?: "Error al cargar",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "$" + String.format("%.2f", fruitPrice),
                color = MaterialTheme.colorScheme.secondary
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = fruitDetail ?: "Error al cargar",
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.height(10.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.5.dp)
        )
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Cantidad", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(10.dp))
            IconButton(onClick = {
                if (count != 1) count--
                cost = fruitPrice!! * count
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.minus),
                    contentDescription = "Icon fruit minus"
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "$count")
            Spacer(modifier = Modifier.height(10.dp))
            IconButton(onClick = {
                count++
                cost = fruitPrice!! * count
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.plus),
                    contentDescription = "Icon fruit plus"
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Text(text = "Costo:", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "$$cost", fontWeight = FontWeight.Light)
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.5.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Valoraciones (10)", modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = "Icon rating",
                tint = Orange60
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(text = "$fruitRating")
        }
        Spacer(modifier = Modifier.height(5.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.secondary)
            } else {
                LazyColumn(
                    content = {
                        items(10) {
                            ValorateItem()
                        }
                    }
                )
            }
        }
        Box(modifier = Modifier.align(Alignment.End)) {
            ExtendedFloatingActionButton(
                onClick = {
                    viewModel.onDetailEvent(DetailEvent.InsertItemCart(fruitName!!, fruitImage!!, count, cost!!))
                },
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Button add to car")
                Text(text = "Añadir al carrito")
            }
        }
    }
}

@Composable
fun ValorateItem() {
    Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("")
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.img_person),
            error = painterResource(id = R.drawable.img_person),
            contentDescription = "",
            modifier = Modifier
                .size(45.dp)
                .clip(CircleShape)
                .background(MercuryDark60)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp, bottom = 5.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "John Casser", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = "Icon rating",
                    tint = Orange60
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "4.5")
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "La manzana esta muy buena, ademas de que tiene una precio excelente. La recomiendo",
                fontWeight = FontWeight.Light,
                fontSize = 14.sp
            )
        }
    }
}