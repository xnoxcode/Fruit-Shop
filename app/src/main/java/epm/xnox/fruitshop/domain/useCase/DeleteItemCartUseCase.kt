package epm.xnox.fruitshop.domain.useCase

import epm.xnox.fruitshop.domain.repository.DatabaseRepository
import javax.inject.Inject

class DeleteItemCartUseCase @Inject constructor(private val repository: DatabaseRepository) {
    suspend operator fun invoke(id: Int) =
        repository.deleteItemCart(id)
}