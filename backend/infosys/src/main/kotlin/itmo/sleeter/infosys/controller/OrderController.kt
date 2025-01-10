package itmo.sleeter.infosys.controller

import itmo.sleeter.infosys.dto.request.OrderRequest
import itmo.sleeter.infosys.dto.response.OrderResponse
import itmo.sleeter.infosys.service.OrderService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/order")
class OrderController(
    private val orderService: OrderService,
    private val simpMessagingTemplate: SimpMessagingTemplate,
) {
    @PostMapping
    @Transactional
    fun createOrder(@RequestBody orderRequest: OrderRequest): ResponseEntity<OrderResponse> {
        val resp = orderService.createOrder(orderRequest)
        simpMessagingTemplate.convertAndSend("/topic/app", "")
        return ResponseEntity.ok(resp)
    }

    @PutMapping("/status/{id}")
    fun updateOrderStatus(@PathVariable id: Long, @RequestParam status: Long): ResponseEntity<Void> {
        orderService.updateOrderStatus(id, status)
        simpMessagingTemplate.convertAndSend("/topic/app", "")
        return ResponseEntity.ok(null)
    }

    @GetMapping
    fun getOrders(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
    ): ResponseEntity<Page<OrderResponse>> {
        val pageable: Pageable = PageRequest.of(page, size)
        return ResponseEntity.ok(orderService.getOrders(pageable))
    }
}