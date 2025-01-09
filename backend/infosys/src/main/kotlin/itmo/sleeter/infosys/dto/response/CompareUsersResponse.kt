package itmo.sleeter.infosys.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class CompareUsersResponse(
    @JsonProperty("before")
    val userResponse: UserResponse,
    @JsonProperty("after")
    val userUpdate: UserUpdateResponse
)
