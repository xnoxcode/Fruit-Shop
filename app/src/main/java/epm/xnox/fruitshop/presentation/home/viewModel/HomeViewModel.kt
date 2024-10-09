package epm.xnox.fruitshop.presentation.home.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import epm.xnox.fruitshop.common.Result
import epm.xnox.fruitshop.domain.model.Fruit
import epm.xnox.fruitshop.domain.useCase.GetFruitsUseCase
import epm.xnox.fruitshop.presentation.home.ui.CategoryType
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getFruitsUseCase: GetFruitsUseCase) : ViewModel() {

    private val _state: MutableState<HomeState> = mutableStateOf(HomeState())
    val state: MutableState<HomeState> = _state

    init {
        onEvent(HomeEvent.GetFruits(CategoryType.Todo))
    }

    fun onEvent(event: HomeEvent) {
        when(event) {
            is HomeEvent.GetFruits -> {
                getFruits(event.category)
            }

            is HomeEvent.OnSearch -> {
                onSearch(event.text)
            }
        }
    }

    private fun getFruits(category: CategoryType) {
        viewModelScope.launch {
            getFruitsUseCase(category).collect { result ->
                when (result) {
                    is Result.Error -> {
                        _state.value = HomeState(error = result.message!!)
                    }

                    is Result.Loading -> {
                        _state.value = HomeState(isLoading = true)
                    }

                    is Result.Success -> {
                        _state.value = HomeState(data = result.data)
                    }
                }
            }
        }
    }

    private fun onSearch(text: String) {
        val result = _state.value.data?.filter {
            it.name.contains(text, ignoreCase = true)
        }
        _state.value = HomeState(searchResult = result ?: emptyList())
    }
}

sealed class HomeEvent {
    data class GetFruits(val category: CategoryType) : HomeEvent()
    data class OnSearch(val text: String) : HomeEvent()
}

data class HomeState(
    val error: String = "",
    val isLoading: Boolean = false,
    val data: List<Fruit>? = emptyList(),
    val searchResult: List<Fruit> = emptyList()
)