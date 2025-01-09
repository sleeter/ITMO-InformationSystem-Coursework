package itmo.sleeter.infosys.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class PaymentResponse(
    val id: Long,
    @JsonProperty("payment_method")
    val paymentsMethod: String,
)
