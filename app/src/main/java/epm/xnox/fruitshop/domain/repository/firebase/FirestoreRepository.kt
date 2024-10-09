package epm.xnox.fruitshop.domain.repository.firebase

import epm.xnox.fruitshop.common.Result
import epm.xnox.fruitshop.domain.model.Fruit
import epm.xnox.fruitshop.domain.model.Valoration
import epm.xnox.fruitshop.presentation.home.ui.CategoryType
import kotlinx.coroutines.flow.Flow

interface FirestoreRepository {
    suspend fun getFruits(category: CategoryType): Flow<Result<List<Fruit>>>
    suspend fun getValorations(): Flow<Result<List<Valoration>>>
}

