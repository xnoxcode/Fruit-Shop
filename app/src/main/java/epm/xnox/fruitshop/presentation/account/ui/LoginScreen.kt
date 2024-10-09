package epm.xnox.fruitshop.presentation.account.ui

import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import epm.xnox.fruitshop.R
import epm.xnox.fruitshop.presentation.account.viewModel.AccountEvent
import epm.xnox.fruitshop.presentation.account.viewModel.AccountViewModel

@Composable
fun LoginScreen(viewModel: AccountViewModel, onNavigate: () -> Unit, onSignUp: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        HeaderLogin(onNavigate)
        ContentLogin(viewModel, onSignUp)
    }
}

@Composable
fun HeaderLogin(onNavigate: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(bottomStart = 22.dp, bottomEnd = 22.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            IconButton(onClick = { onNavigate() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Icon back")
            }
        }
        Image(painter = painterResource(id = R.drawable.img_account_login), contentDescription = "")
        Text(
            text = "Hola\nBienvenido",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ContentLogin(viewModel: AccountViewModel, onSignUp: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val state = viewModel.state.value

    if (state.error.isNotBlank()) {
        LaunchedEffect(null) {
            Toast.makeText(context, "Ocurrio un error", Toast.LENGTH_LONG).show()
        }
    }

    Column(
        modifier = Modifier.padding(15.dp)
    ) {
        Text(text = "Iniciar Sesión", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(15.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp),
                value = email,
                onValueChange = { email = it },
                singleLine = true,
                label = { Text(text = "Email") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = MaterialTheme.colorScheme.secondary,
                    focusedBorderColor = MaterialTheme.colorScheme.secondary,
                    cursorColor = MaterialTheme.colorScheme.secondary
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = "Icon email"
                    )
                }
            )
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp),
                value = password,
                onValueChange = { password = it },
                singleLine = true,
                label = { Text(text = "Contraseña") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedLabelColor = MaterialTheme.colorScheme.secondary,
                    focusedBorderColor = MaterialTheme.colorScheme.secondary,
                    cursorColor = MaterialTheme.colorScheme.secondary
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = "Icon password"
                    )
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Olvidaste tu contraseña?",
                textAlign = TextAlign.End,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))
            OutlinedButton(
                onClick = { viewModel.onAccountEvent(AccountEvent.LoginWithEmailAndPassword(email, password)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.background
                )
            ) {
                if (state.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    Text(text = "Continuar")
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.5.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "También puedes registrarte con",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Light,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(painterResource(id = R.drawable.google), contentDescription = "Icon Goolge")
                }
                Spacer(modifier = Modifier.width(5.dp))
                Divider(
                    modifier = Modifier
                        .height(30.dp)
                        .width(1.5.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painterResource(id = R.drawable.facebook),
                        contentDescription = "Icon Facebook"
                    )
                }
                Spacer(modifier = Modifier.width(5.dp))
                Divider(
                    modifier = Modifier
                        .height(30.dp)
                        .width(1.5.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(painterResource(id = R.drawable.twitter), contentDescription = "Icon X")
                }
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 15.dp, end = 15.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "No tienes cuenta?", fontWeight = FontWeight.Light)
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "Registrarse",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    onSignUp()
                }
            )
        }
    }
}
