package itmo.sleeter.infosys.service

import itmo.sleeter.infosys.dto.response.PickupPointResponse
import itmo.sleeter.infosys.mapper.PickupPointMapper
import itmo.sleeter.infosys.model.PickupPoint
import itmo.sleeter.infosys.repository.PickupPointRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PickupPointService(
    private val pickupPointRepository: PickupPointRepository,
    private val pickupPointMapper: PickupPointMapper
) {
    fun getPickupPointById(id: Long): PickupPoint? = pickupPointRepository.findByIdOrNull(id)
    fun getPickupPoints(pageable: Pageable): Page<PickupPointResponse> {
        val page = pickupPointRepository.findAll(pageable).map { pickupPointMapper.pickupPointToPickupPointResponse(it) }
        return PageImpl(page.content, pageable, page.totalElements)
    }
}