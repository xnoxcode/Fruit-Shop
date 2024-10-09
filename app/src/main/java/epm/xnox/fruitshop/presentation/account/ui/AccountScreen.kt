package epm.xnox.fruitshop.presentation.account.ui

import androidx.compose.runtime.Composable
import epm.xnox.fruitshop.presentation.account.viewModel.AccountViewModel

@Composable
fun AccountScreen(viewModel: AccountViewModel, onNavigate: () -> Unit, onSignUp: () -> Unit) {

    val state = viewModel.state.value

    if (state.isUserLogin) {
        UserInfoScreen(viewModel = viewModel) {
            onNavigate()
        }
    } else {
        LoginScreen(
            viewModel = viewModel,
            onNavigate = { onNavigate() },
            onSignUp = { onSignUp() }
        )
    }
}