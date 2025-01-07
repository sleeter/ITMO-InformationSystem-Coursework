package itmo.sleeter.infosys.controller

import itmo.sleeter.infosys.dto.request.OrderRequest
import itmo.sleeter.infosys.dto.response.OrderResponse
import itmo.sleeter.infosys.service.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/order")
class OrderController(
    private val orderService: OrderService
) {
    @PostMapping
    @Transactional
    fun createOrder(@RequestBody orderRequest: OrderRequest): ResponseEntity<OrderResponse> {
        return ResponseEntity.ok(orderService.createOrder(orderRequest))
    }

    @PutMapping("/status/{id}")
    fun updateOrderStatus(@PathVariable id: Long, @RequestParam status: Long): ResponseEntity<Void> {
        orderService.updateOrderStatus(id, status)
        return ResponseEntity.ok(null)
    }

    @GetMapping
    fun getOrders(): ResponseEntity<List<OrderResponse>> {
        return ResponseEntity.ok(orderService.getOrders())
    }
}