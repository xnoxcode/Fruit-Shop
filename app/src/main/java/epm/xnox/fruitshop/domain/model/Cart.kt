package epm.xnox.fruitshop.domain.model

import epm.xnox.fruitshop.data.sources.database.entities.CartEntity

data class Cart(
    val id: Int,
    val name: String,
    val image: String,
    val count: Int,
    val cost: Float
)

fun CartEntity.toDomain() = Cart(
    id = id,
    name = name,
    image = image,
    count = count,
    cost = cost
)
