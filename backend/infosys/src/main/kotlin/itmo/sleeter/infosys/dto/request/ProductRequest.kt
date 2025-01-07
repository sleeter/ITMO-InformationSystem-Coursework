package itmo.sleeter.infosys.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class ProductRequest(
    @JsonProperty("product_id")
    val productId: Long,
    val count: Int
)
