package itmo.sleeter.infosys.dto.response

import java.math.BigDecimal
import java.time.Instant

data class OrderResponse(
    val id: Long,
    val customer: CustomerResponse,
    val size: Long,
    val date: Instant,
    val status: String,
    val pickupPoint: PickupPointResponse,
    val totalPrice: BigDecimal,
    val payment: String,
    val products: List<OrderedProductResponse>
)
