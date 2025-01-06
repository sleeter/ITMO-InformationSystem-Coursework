package itmo.sleeter.infosys.mapper

import itmo.sleeter.infosys.dto.response.UserCreateResponse
import itmo.sleeter.infosys.model.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.springframework.stereotype.Component

@Mapper
@Component
interface UserMapper {
    @Mapping(target = "role", source = "role.name")
    @Mapping(target = "pickupPointId", source = "pickupPoint.id")
    fun userToUserCreateResponse(user: User): UserCreateResponse
}