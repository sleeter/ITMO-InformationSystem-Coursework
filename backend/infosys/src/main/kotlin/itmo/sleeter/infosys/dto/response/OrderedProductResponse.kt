package itmo.sleeter.infosys.dto.response

data class OrderedProductResponse(
    val product: ProductResponse,
    val count: Int
)
