package itmo.sleeter.infosys.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class UserResponse(
    val id: Long,
    val login: String,
    val password: String,
    val role: String,
    val name: String,
    @JsonProperty("pick_up_point_id")
    val pickupPointId: Long? = null,
)
