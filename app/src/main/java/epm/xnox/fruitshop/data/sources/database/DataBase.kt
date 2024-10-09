package epm.xnox.fruitshop.data.sources.database

import androidx.room.Database
import androidx.room.RoomDatabase
import epm.xnox.fruitshop.data.sources.database.dao.CartDao
import epm.xnox.fruitshop.data.sources.database.entities.CartEntity

@Database(entities = [CartEntity::class], version = 1)
abstract class DataBase: RoomDatabase() {
    abstract fun getItemsCartDao(): CartDao
}