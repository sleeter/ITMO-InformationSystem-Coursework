package itmo.sleeter.infosys.mapper

import itmo.sleeter.infosys.dto.response.CompareUsersResponse
import itmo.sleeter.infosys.dto.response.UserResponse
import itmo.sleeter.infosys.dto.response.UserUpdateResponse
import itmo.sleeter.infosys.model.User
import itmo.sleeter.infosys.model.UserUpdate
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.springframework.stereotype.Component

@Mapper
@Component
interface UserMapper {
    @Mapping(target = "role", source = "role.name")
    @Mapping(target = "pickupPointId", source = "pickupPoint.id")
    fun userToUserResponse(user: User): UserResponse

    @Mapping(target = "role", source = "role.name")
    @Mapping(target = "pickupPointId", source = "pickupPoint.id")
    fun userToUserUpdateResponse(userUpdate: UserUpdate): UserUpdateResponse
}