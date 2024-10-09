package epm.xnox.fruitshop.domain.model

data class Fruit(
    val id: String,
    val name: String,
    val detail: String,
    val image: String,
    val price: Float,
    val rating: Float
) {
    constructor() : this("", "","", "", 0.0f, 0.0f)
}
