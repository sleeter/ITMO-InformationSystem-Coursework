package itmo.sleeter.infosys.repository

import itmo.sleeter.infosys.model.PickupPoint
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PickupPointRepository : JpaRepository<PickupPoint, Long> {
}