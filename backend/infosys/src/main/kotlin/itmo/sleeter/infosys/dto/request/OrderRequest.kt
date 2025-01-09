package itmo.sleeter.infosys.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class OrderRequest(
    @JsonProperty("customer_id")
    val customerId: Long,
    val size: Long,
    @JsonProperty("pick_up_point_id")
    val pickupPointId: Long,
    @JsonProperty("total_price")
    val totalPrice: BigDecimal,
    @JsonProperty("payment_id")
    val paymentId: Long,
    val products: List<ProductRequest>
)
