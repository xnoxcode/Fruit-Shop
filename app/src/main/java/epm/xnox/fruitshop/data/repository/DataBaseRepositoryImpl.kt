package epm.xnox.fruitshop.data.repository

import epm.xnox.fruitshop.data.sources.database.dao.CartDao
import epm.xnox.fruitshop.data.sources.database.entities.CartEntity
import epm.xnox.fruitshop.domain.model.Cart
import epm.xnox.fruitshop.domain.model.toDomain
import epm.xnox.fruitshop.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataBaseRepositoryImpl @Inject constructor(private val cartDao: CartDao) :
    DatabaseRepository {

    override suspend fun insertItemCart(item: CartEntity) {
        cartDao.insertItemCart(item)
    }

    override suspend fun getItemsCart(): Flow<List<Cart>> = flow {
        val items = cartDao.getItemsCart()

        emitAll(
            items.map { item ->
                item.map { it.toDomain() }
            }
        )
    }

    override suspend fun deleteItemCart(id: Int) {
        cartDao.deleteItemCart(id)
    }

    override suspend fun deleteItems() {
        cartDao.deleteItemsCart()
    }
}