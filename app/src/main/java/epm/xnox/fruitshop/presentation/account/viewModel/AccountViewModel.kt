package epm.xnox.fruitshop.presentation.account.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import epm.xnox.fruitshop.common.Result
import epm.xnox.fruitshop.domain.useCase.GetPreferenceBooleanValueUseCase
import epm.xnox.fruitshop.domain.useCase.GetPreferenceStringValueUseCase
import epm.xnox.fruitshop.domain.useCase.LoginWithCredentialUseCase
import epm.xnox.fruitshop.domain.useCase.LoginWithEmailAndPasswordUseCase
import epm.xnox.fruitshop.domain.useCase.PutPreferenceBooleanValueUseCase
import epm.xnox.fruitshop.domain.useCase.PutPreferenceStringValueUseCase
import epm.xnox.fruitshop.domain.useCase.SignUpUseCase
import epm.xnox.fruitshop.presentation.account.util.PreferenceKey
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val getPreferenceBooleanValueUseCase: GetPreferenceBooleanValueUseCase,
    private val getPreferenceStringValueUseCase: GetPreferenceStringValueUseCase,
    private val putPreferenceBooleanValueUseCase: PutPreferenceBooleanValueUseCase,
    private val putPreferenceStringValueUseCase: PutPreferenceStringValueUseCase,
    private val loginWithEmailAndPasswordUseCase: LoginWithEmailAndPasswordUseCase,
    private val loginWithCredentialUseCase: LoginWithCredentialUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _state: MutableState<AccountState> = mutableStateOf(AccountState())
    val state: MutableState<AccountState> = _state

    init {
        onAccountEvent(AccountEvent.GetPreferenceBooleanValue(PreferenceKey.USER_LOGIN_KEY.name))
    }

    fun onAccountEvent(event: AccountEvent) {
        when (event) {
            is AccountEvent.PutPreferenceBooleanValue -> {
                savePreferenceBooleanValue(event.key, event.value)
            }

            is AccountEvent.GetPreferenceBooleanValue -> {
                getPreferenceBooleanValue(event.key)
            }

            is AccountEvent.LoginWithEmailAndPassword -> {
                loginWithEmailAndPassword(event.email, event.password)
            }

            is AccountEvent.SignUpUser -> {
                signUpUser(event.name, event.email, event.password)
            }

            AccountEvent.LogoutUser -> {
                _state.value = AccountState(isUserLogin = false)
                onAccountEvent(AccountEvent.PutPreferenceBooleanValue(PreferenceKey.USER_LOGIN_KEY.name, false))
            }

            is AccountEvent.LoginWithCredential -> {

            }

            is AccountEvent.GetPreferenceStringValue -> {
                getPreferenceStringValue(event.key)
            }

            is AccountEvent.PutPreferenceStringValue -> {
                savePreferenceStringValue(event.key, event.value)
            }
        }
    }

    private fun getPreferenceBooleanValue(key: String) {
        viewModelScope.launch {
            getPreferenceBooleanValueUseCase(key)?.let {
                _state.value = AccountState(isUserLogin = it)
            }
        }
    }

    private fun getPreferenceStringValue(key: String) {
        viewModelScope.launch {
            getPreferenceStringValueUseCase(key)?.let {
                _state.value = AccountState(userEmail = it)
            }
        }
    }

    private fun savePreferenceBooleanValue(key: String, value: Boolean) {
        viewModelScope.launch {
            putPreferenceBooleanValueUseCase(key, value)
        }
    }

    private fun savePreferenceStringValue(key: String, value: String) {
        viewModelScope.launch {
            putPreferenceStringValueUseCase(key, value)
        }
    }

    private fun loginWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            loginWithEmailAndPasswordUseCase(email, password).collect { result ->
                when (result) {
                    is Result.Error -> {
                        _state.value = AccountState(isLoading = false)
                        _state.value = AccountState(error = result.message ?: "Ocurrio un error")
                    }

                    is Result.Loading -> {
                        _state.value = AccountState(isLoading = true)
                    }

                    is Result.Success -> {
                        onAccountEvent(AccountEvent.PutPreferenceBooleanValue(PreferenceKey.USER_LOGIN_KEY.name, true))
                        onAccountEvent(AccountEvent.PutPreferenceStringValue(PreferenceKey.USER_EMAIL_KEY.name, result.data!!))
                        _state.value = AccountState(isLoading = false)
                        _state.value = AccountState(isUserLogin = true)
                    }
                }
            }
        }
    }

    private fun loginWithCredential(credential: AuthCredential) {
        viewModelScope.launch {
            loginWithCredentialUseCase(credential).collect { result ->
                when (result) {
                    is Result.Error -> {
                        _state.value = AccountState(isLoading = false)
                        _state.value = AccountState(error = result.message ?: "Ocurrio un error")
                    }

                    is Result.Loading -> {
                        _state.value = AccountState(isLoading = true)
                    }

                    is Result.Success -> {
                        onAccountEvent(AccountEvent.PutPreferenceBooleanValue(PreferenceKey.USER_LOGIN_KEY.name, true))
                        _state.value = AccountState(isLoading = false)
                        _state.value = AccountState(isUserLogin = true)
                    }
                }
            }
        }
    }

    private fun signUpUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            signUpUseCase(name, email, password).collect { result ->
                when (result) {
                    is Result.Error -> {
                        _state.value = AccountState(isLoading = false)
                        _state.value = AccountState(error = result.message ?: "Ocurrio un error")
                    }

                    is Result.Loading -> {
                        _state.value = AccountState(isLoading = true)
                    }

                    is Result.Success -> {
                        _state.value = AccountState(isLoading = false)
                        _state.value = AccountState(isUserSignUp = true)
                    }
                }
            }
        }
    }
}

sealed class AccountEvent {
    data class GetPreferenceBooleanValue(val key: String) : AccountEvent()
    data class GetPreferenceStringValue(val key: String) : AccountEvent()
    data class LoginWithEmailAndPassword(val email: String, val password: String) : AccountEvent()
    data class LoginWithCredential(val credential: AuthCredential) : AccountEvent()
    data class PutPreferenceBooleanValue(val key: String, val value: Boolean) : AccountEvent()
    data class PutPreferenceStringValue(val key: String, val value: String) : AccountEvent()
    data class SignUpUser(val name: String, val email: String, val password: String) : AccountEvent()
    data object LogoutUser : AccountEvent()
}

data class AccountState(
    val isUserLogin: Boolean = false,
    val isUserSignUp: Boolean = false,
    val isLoading: Boolean = false,
    val userEmail: String = "",
    val error: String = ""
)