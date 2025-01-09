package itmo.sleeter.infosys.controller

import itmo.sleeter.infosys.dto.response.CustomerResponse
import itmo.sleeter.infosys.service.CustomerService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/customer")
class CustomerController(
    private val customerService: CustomerService
) {
    @GetMapping
    fun getCustomers(
    ): ResponseEntity<List<CustomerResponse>> {
        return ResponseEntity.ok(customerService.getCustomers())
    }
}