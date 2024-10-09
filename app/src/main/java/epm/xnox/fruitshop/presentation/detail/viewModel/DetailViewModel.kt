package epm.xnox.fruitshop.presentation.detail.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import epm.xnox.fruitshop.common.Result
import epm.xnox.fruitshop.data.sources.database.entities.CartEntity
import epm.xnox.fruitshop.domain.model.Valoration
import epm.xnox.fruitshop.domain.useCase.GetValorationUseCase
import epm.xnox.fruitshop.domain.useCase.InsertItemCartUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val insertItemCartUseCase: InsertItemCartUseCase,
    private val getValorationUseCase: GetValorationUseCase
) : ViewModel() {

    private val _state: MutableState<DetailState> = mutableStateOf(DetailState())
    val state: MutableState<DetailState> = _state

    init {
        onDetailEvent(DetailEvent.GetValorations)
    }

    fun onDetailEvent(event: DetailEvent) {
        when (event) {
            DetailEvent.GetValorations -> {
                getValoration()
            }

            is DetailEvent.InsertItemCart -> {
                insertItemCart(event.name, event.image, event.count, event.cost)
            }
        }
    }

    private fun insertItemCart(name: String, image: String, count: Int, cost: Float) {
        viewModelScope.launch {
            insertItemCartUseCase(
                CartEntity(
                    name = name,
                    image = image,
                    count = count,
                    cost = cost
                )
            )
        }
    }

    private fun getValoration() {
        viewModelScope.launch {
            getValorationUseCase().collect { result ->
                when (result) {
                    is Result.Error -> {
                        _state.value = DetailState(isLoading = false)
                        _state.value = DetailState(error = result.message ?: "Ocurrio un error")
                    }

                    is Result.Loading -> {
                        _state.value = DetailState(isLoading = true)
                    }

                    is Result.Success -> {
                        _state.value = DetailState(isLoading = false)
                        _state.value = DetailState(data = result.data)
                    }
                }
            }
        }
    }
}

sealed class DetailEvent {
    data object GetValorations : DetailEvent()
    data class InsertItemCart(val name: String, val image: String, val count: Int, val cost: Float) : DetailEvent()
}

data class DetailState(
    val isLoading: Boolean = false,
    val data: List<Valoration>? = emptyList(),
    val error: String = ""
)