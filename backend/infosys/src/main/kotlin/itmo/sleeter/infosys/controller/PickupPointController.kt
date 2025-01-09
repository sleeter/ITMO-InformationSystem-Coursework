package itmo.sleeter.infosys.controller

import itmo.sleeter.infosys.dto.response.PickupPointResponse
import itmo.sleeter.infosys.service.PickupPointService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/pickup-point")
class PickupPointController(
    private val pickupPointService: PickupPointService
) {
    @GetMapping
    fun getPickupPoints(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
    ): ResponseEntity<Page<PickupPointResponse>> {
        val pageable: Pageable = PageRequest.of(page, size)
        return ResponseEntity.ok(pickupPointService.getPickupPoints(pageable))
    }
}