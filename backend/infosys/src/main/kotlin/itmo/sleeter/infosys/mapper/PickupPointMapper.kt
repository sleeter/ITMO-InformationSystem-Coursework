package itmo.sleeter.infosys.mapper

import itmo.sleeter.infosys.dto.response.PickupPointResponse
import itmo.sleeter.infosys.model.PickupPoint
import org.mapstruct.Mapper
import org.springframework.stereotype.Component

@Mapper
@Component
interface PickupPointMapper {
    fun pickupPointToPickupPointResponse(pickupPoint: PickupPoint): PickupPointResponse
}