package itmo.sleeter.infosys.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotEmpty

data class UserUpdateRequest(
    @NotEmpty
    val login: String,
    val role: String,
    val name: String,
    @JsonProperty("pick_up_point_id")
    val pickUpPointId: Long? = null,
    val deleted: Boolean = false
)
