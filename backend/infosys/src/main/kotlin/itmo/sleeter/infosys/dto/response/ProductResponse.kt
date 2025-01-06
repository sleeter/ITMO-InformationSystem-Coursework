package itmo.sleeter.infosys.dto.response

data class ProductResponse(
    val id: Long,
    val name: String,
    val size: Long,
    val description: String,
    val category: String,
)
