package epm.xnox.fruitshop.data.repository.firebase

import com.google.firebase.firestore.CollectionReference
import epm.xnox.fruitshop.common.Result
import epm.xnox.fruitshop.domain.model.Fruit
import epm.xnox.fruitshop.domain.model.Valoration
import epm.xnox.fruitshop.domain.repository.firebase.FirestoreRepository
import epm.xnox.fruitshop.presentation.home.ui.CategoryType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreRepositoryImpl @Inject constructor(
    private val fruitList: CollectionReference
) : FirestoreRepository {
    override suspend fun getFruits(category: CategoryType): Flow<Result<List<Fruit>>> = flow {
        try {
            emit(Result.Loading)

            val fruits: List<Fruit> = fruitList.get().await().map { document ->
                document.toObject(Fruit::class.java)
            }
            emit(Result.Success(data = fruits))

        } catch (e: Exception) {
            emit(Result.Error(message = e.localizedMessage ?: "Error desconocido"))
        }
    }

    override suspend fun getValorations(): Flow<Result<List<Valoration>>> = flow {
        emit(Result.Loading)
        delay(3000L)
        emit(Result.Success(emptyList()))
    }
}