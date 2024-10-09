package epm.xnox.fruitshop.data.sources.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import epm.xnox.fruitshop.data.sources.database.entities.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Insert
    suspend fun insertItemCart(note: CartEntity)

    @Query("SELECT * FROM cart_table order by id DESC")
    fun getItemsCart(): Flow<List<CartEntity>>

    @Query("DELETE FROM cart_table")
    suspend fun deleteItemsCart()

    @Query("DELETE FROM cart_table WHERE id = :id")
    suspend fun deleteItemCart(id: Int)
}