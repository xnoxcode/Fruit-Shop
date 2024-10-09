package epm.xnox.fruitshop.presentation.account.ui

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import epm.xnox.fruitshop.R
import epm.xnox.fruitshop.presentation.account.viewModel.AccountEvent
import epm.xnox.fruitshop.presentation.account.viewModel.AccountViewModel
import epm.xnox.fruitshop.ui.theme.MercuryDark60

@Composable
fun UserInfoScreen(viewModel: AccountViewModel, onNavigate: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        HeaderUserInfo(onNavigate, viewModel)
        Spacer(modifier = Modifier.height(15.dp))
        ContentUserInfo(viewModel)
    }
}

@Composable
fun HeaderUserInfo(onNavigate: () -> Unit, viewModel: AccountViewModel) {
    val state = viewModel.state.value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(bottomStart = 22.dp, bottomEnd = 22.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, top = 15.dp)
        ) {
            IconButton(onClick = { onNavigate() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Icon back")
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("")
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.img_person),
                error = painterResource(id = R.drawable.img_person),
                contentDescription = "",
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MercuryDark60)
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = state.userEmail,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ContentUserInfo(viewModel: AccountViewModel) {
    Column(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxSize()
    ) {
        Text(text = "Cuenta", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = "Icon settings",
                modifier = Modifier.padding(start = 15.dp)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(text = "Preferencias")
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = "Icon edit account",
                modifier = Modifier.padding(start = 15.dp)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(text = "Editar cuenta")
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable {
                    viewModel.onAccountEvent(AccountEvent.LogoutUser)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = "Icon logout",
                modifier = Modifier.padding(start = 15.dp)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(text = "Cerrar sesión")
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = "Icon delete account",
                modifier = Modifier.padding(start = 15.dp)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(text = "Eliminar cuenta")
        }
        Spacer(modifier = Modifier.height(15.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.5.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Información", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Email,
                contentDescription = "Icon contact",
                modifier = Modifier.padding(start = 15.dp)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Text(text = "Contactar con la tienda", modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowRight,
                contentDescription = "Icon contact",
                modifier = Modifier.padding(end = 15.dp)
            )
        }
        Box(
            modifier = Modifier
                .padding(bottom = 15.dp)
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = "Desarrollado por X-NOX Development",
                fontWeight = FontWeight.Light,
                fontSize = 14.sp
            )
        }
    }
}