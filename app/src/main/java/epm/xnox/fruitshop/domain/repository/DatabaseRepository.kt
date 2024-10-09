package epm.xnox.fruitshop.domain.repository

import epm.xnox.fruitshop.data.sources.database.entities.CartEntity
import epm.xnox.fruitshop.domain.model.Cart
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    suspend fun insertItemCart(item: CartEntity)
    suspend fun getItemsCart(): Flow<List<Cart>>
    suspend fun deleteItemCart(id: Int)
    suspend fun deleteItems()
}