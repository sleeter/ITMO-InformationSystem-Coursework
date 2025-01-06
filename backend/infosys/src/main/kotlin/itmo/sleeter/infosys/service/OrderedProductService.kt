package itmo.sleeter.infosys.service

import itmo.sleeter.infosys.repository.OrderedProductRepository
import org.springframework.stereotype.Service

@Service
class OrderedProductService(
    private val orderedProductRepository: OrderedProductRepository
) {
}