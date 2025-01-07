package itmo.sleeter.infosys.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class CustomerResponse(
    val name: String,
    val email: String,
    val age: Int,
    @JsonProperty("phone_number")
    val phoneNumber: String,
)
