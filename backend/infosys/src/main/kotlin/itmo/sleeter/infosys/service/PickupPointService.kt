package itmo.sleeter.infosys.service

import itmo.sleeter.infosys.model.PickupPoint
import itmo.sleeter.infosys.repository.PickupPointRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PickupPointService(
    private val pickupPointRepository: PickupPointRepository
) {
    fun getPickupPointById(id: Long): PickupPoint? = pickupPointRepository.findByIdOrNull(id)
}