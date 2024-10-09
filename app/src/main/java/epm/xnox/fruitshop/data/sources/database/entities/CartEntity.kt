package epm.xnox.fruitshop.data.sources.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_table")
class CartEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "image") val image: String = "",
    @ColumnInfo(name = "count") val count: Int,
    @ColumnInfo(name = "cost") val cost: Float
)