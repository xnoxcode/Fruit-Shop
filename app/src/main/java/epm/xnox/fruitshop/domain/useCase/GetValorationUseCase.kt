package epm.xnox.fruitshop.domain.useCase

import epm.xnox.fruitshop.common.Result
import epm.xnox.fruitshop.domain.model.Valoration
import epm.xnox.fruitshop.domain.repository.firebase.FirestoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetValorationUseCase @Inject constructor(private val repository: FirestoreRepository) {
    suspend operator fun invoke(): Flow<Result<List<Valoration>>> =
        repository.getValorations()
}