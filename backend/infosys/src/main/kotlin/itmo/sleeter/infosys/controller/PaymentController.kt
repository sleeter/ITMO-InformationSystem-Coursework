package itmo.sleeter.infosys.controller

import itmo.sleeter.infosys.dto.response.PaymentResponse
import itmo.sleeter.infosys.service.PaymentService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/payment")
class PaymentController(
    private val paymentService: PaymentService
) {
    @GetMapping
    fun getPayments(
    ): ResponseEntity<List<PaymentResponse>> {
        return ResponseEntity.ok(paymentService.getPayments())
    }
}