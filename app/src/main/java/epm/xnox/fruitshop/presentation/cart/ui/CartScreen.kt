package epm.xnox.fruitshop.presentation.cart.ui

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import epm.xnox.fruitshop.R
import epm.xnox.fruitshop.domain.model.Cart
import epm.xnox.fruitshop.presentation.cart.viewModel.CartEvent
import epm.xnox.fruitshop.presentation.cart.viewModel.CartViewModel

@Composable
fun CartScreen(viewModel: CartViewModel, onNavigate: () -> Unit) {

    Column(modifier = Modifier.fillMaxSize()) {
        Header(onNavigate, viewModel)
        Spacer(modifier = Modifier.height(10.dp))
        Box(modifier = Modifier.weight(1f)) {
            Content(viewModel)
        }
        Spacer(modifier = Modifier.height(15.dp))
        Footer(viewModel)
    }
}

@Composable
fun Header(onNavigate: () -> Unit, viewModel: CartViewModel) {
    Column(
        modifier = Modifier.fillMaxWidth()
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
            Text(
                text = "Mi carrito",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            IconButton(onClick = { viewModel.onEvent(CartEvent.DeleteItemsCart) }) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete cart menu"
                )
            }
        }
    }
}

@Composable
fun Content(viewModel: CartViewModel) {
    val state = viewModel.state.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(22.dp)
            )
    ) {
        LazyColumn(content = {
            items(state.items) { item ->
                CartItem(item, viewModel)
            }
        }, modifier = Modifier.padding(10.dp))
    }
}

@Composable
fun CartItem(item: Cart, viewModel: CartViewModel) {
    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(100.dp)
            .background(
                color = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(120.dp)
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
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${item.name} (${item.count})",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = "$" + String.format("%.2f", item.cost), fontWeight = FontWeight.Light)
        }
        IconButton(
            onClick = { viewModel.onEvent(CartEvent.DeleteItemCart(item.id)) },
            modifier = Modifier.padding(end = 10.dp)
        ) {
            Icon(imageVector = Icons.Filled.Delete, contentDescription = "Icon delete item")
        }
    }
}

@Composable
fun Footer(viewModel: CartViewModel) {
    val state = viewModel.state.value

    if (state.items.isNotEmpty())
        viewModel.onEvent(CartEvent.SubtotalCost)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, bottom = 15.dp)
    ) {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.5.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Subtotal:", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "${state.subtotal}", fontWeight = FontWeight.Light)
            Spacer(modifier = Modifier.width(15.dp))
            Text(text = "Envio:", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = if (state.items.isNotEmpty()) "$10.00" else "$0.00",
                fontWeight = FontWeight.Light
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (state.items.isNotEmpty()) "$${state.subtotal + 10.0f}" else "$0.00",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                modifier = Modifier.weight(1f)
            )
            ExtendedFloatingActionButton(
                onClick = { /*TODO*/ },
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(imageVector = Icons.Filled.ArrowForward, contentDescription = "Button pay now")
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Pagar ahora")
            }
        }
    }
}