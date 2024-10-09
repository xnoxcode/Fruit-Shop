package epm.xnox.fruitshop.domain.useCase

import epm.xnox.fruitshop.domain.model.Cart
import epm.xnox.fruitshop.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetItemsCartUseCase @Inject constructor(private val repository: DatabaseRepository) {
    suspend operator fun invoke(): Flow<List<Cart>> =
        repository.getItemsCart()
}