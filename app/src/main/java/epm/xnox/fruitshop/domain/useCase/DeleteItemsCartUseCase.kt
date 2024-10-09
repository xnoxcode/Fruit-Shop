package epm.xnox.fruitshop.domain.useCase

import epm.xnox.fruitshop.domain.repository.DatabaseRepository
import javax.inject.Inject

class DeleteItemsCartUseCase @Inject constructor(private val repository: DatabaseRepository) {
    suspend operator fun invoke() =
        repository.deleteItems()
}