package itmo.sleeter.infosys.service

import itmo.sleeter.infosys.dto.request.ProductRequest
import itmo.sleeter.infosys.model.Order
import itmo.sleeter.infosys.model.OrderedProduct
import itmo.sleeter.infosys.model.Product
import itmo.sleeter.infosys.repository.OrderedProductRepository
import org.springframework.stereotype.Service

@Service
class OrderedProductService(
    private val orderedProductRepository: OrderedProductRepository
) {
    fun saveOrderedProduct(product: Product, order: Order, count: Int) {
        val orderedProduct = OrderedProduct()
        orderedProduct.order = order
        orderedProduct.product = product
        orderedProduct.count = count
        orderedProductRepository.save(orderedProduct)
    }
}