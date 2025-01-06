package itmo.sleeter.infosys.service

import itmo.sleeter.infosys.repository.OrderRepository
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository
) {
}