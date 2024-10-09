package epm.xnox.fruitshop.presentation.cart.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import epm.xnox.fruitshop.domain.model.Cart
import epm.xnox.fruitshop.domain.useCase.DeleteItemCartUseCase
import epm.xnox.fruitshop.domain.useCase.DeleteItemsCartUseCase
import epm.xnox.fruitshop.domain.useCase.GetItemsCartUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getItemsCartUseCase: GetItemsCartUseCase,
    private val deleteItemCartUseCase: DeleteItemCartUseCase,
    private val deleteItemsCartUseCase: DeleteItemsCartUseCase
) : ViewModel() {

    private val _state: MutableState<CartState> = mutableStateOf(CartState())
    var state: State<CartState> = _state

    init {
        onEvent(CartEvent.GetItemsCart)
    }

    fun onEvent(event: CartEvent) {
        when (event) {
            is CartEvent.DeleteItemCart -> {
                deleteItemCart(event.id)
            }

            CartEvent.DeleteItemsCart -> {
                deleteItemsCart()
            }

            CartEvent.GetItemsCart -> {
                getItemsCart()
            }

            CartEvent.SubtotalCost -> subtotalCost()
        }
    }

    private fun getItemsCart() {
        viewModelScope.launch {
            getItemsCartUseCase().collect { items ->
                _state.value = CartState(items = items)
            }
        }
    }

    private fun deleteItemCart(id: Int) {
        viewModelScope.launch {
            deleteItemCartUseCase(id)
        }
    }

    private fun deleteItemsCart() {
        viewModelScope.launch {
            deleteItemsCartUseCase()
        }
    }

    private fun subtotalCost() {
        var subtotal = 0.0f
        _state.value.items.forEach { item ->
            subtotal += subtotal + item.cost
        }
        _state.value = _state.value.copy(subtotal = subtotal)
    }
}

sealed class CartEvent {
    data class DeleteItemCart(val id: Int) : CartEvent()
    data object DeleteItemsCart : CartEvent()
    data object GetItemsCart : CartEvent()
    data object SubtotalCost : CartEvent()
}

data class CartState(
    val items: List<Cart> = emptyList(),
    val subtotal: Float = 0.0f
)
