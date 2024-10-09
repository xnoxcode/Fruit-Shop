package epm.xnox.fruitshop.domain.useCase

import epm.xnox.fruitshop.data.sources.database.entities.CartEntity
import epm.xnox.fruitshop.domain.repository.DatabaseRepository
import javax.inject.Inject

class InsertItemCartUseCase @Inject constructor(private val repository: DatabaseRepository) {
    suspend operator fun invoke(item: CartEntity) =
        repository.insertItemCart(item)
}