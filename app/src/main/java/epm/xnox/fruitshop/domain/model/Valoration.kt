package epm.xnox.fruitshop.domain.model

data class Valoration(
    val id: Int,
    val name: String,
    val photo: String,
    val valoration: String,
    val rating: Float
)  {
    constructor() : this(0, "", "", "", 0.0f)
}
