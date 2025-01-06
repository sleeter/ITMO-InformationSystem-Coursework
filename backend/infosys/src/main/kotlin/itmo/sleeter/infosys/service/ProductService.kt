package itmo.sleeter.infosys.service

import itmo.sleeter.infosys.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository
) {
}