package epm.xnox.fruitshop.domain.useCase

import epm.xnox.fruitshop.common.Result
import epm.xnox.fruitshop.domain.model.Fruit
import epm.xnox.fruitshop.domain.repository.firebase.FirestoreRepository
import epm.xnox.fruitshop.presentation.home.ui.CategoryType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFruitsUseCase @Inject constructor(private val repository: FirestoreRepository) {
    suspend operator fun invoke(category: CategoryType): Flow<Result<List<Fruit>>> =
        repository.getFruits(category)
}