package itmo.sleeter.infosys.controller

import itmo.sleeter.infosys.dto.response.StatusResponse
import itmo.sleeter.infosys.service.StatusService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/status")
class StatusController(
    private val service: StatusService
){
    @GetMapping
    fun getStatuses(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
    ): ResponseEntity<Page<StatusResponse>> {
        val pageable: Pageable = PageRequest.of(page, size)
        return ResponseEntity.ok(service.getStatuses(pageable))
    }
}