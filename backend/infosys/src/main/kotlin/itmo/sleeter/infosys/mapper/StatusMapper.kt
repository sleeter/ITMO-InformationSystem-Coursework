package itmo.sleeter.infosys.mapper

import itmo.sleeter.infosys.dto.response.StatusResponse
import itmo.sleeter.infosys.model.Status
import org.mapstruct.Mapper
import org.springframework.stereotype.Component

@Mapper
@Component
interface StatusMapper {
    fun statusToStatusResponse(status: Status): StatusResponse
}