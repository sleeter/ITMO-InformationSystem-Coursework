package itmo.sleeter.infosys.service

import itmo.sleeter.infosys.dto.response.ProductResponse
import itmo.sleeter.infosys.mapper.ProductMapper
import itmo.sleeter.infosys.model.Product
import itmo.sleeter.infosys.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val productMapper: ProductMapper
) {
    fun getProducts(): List<ProductResponse> {
        val res = productRepository.findAll().map { productMapper.productToProductResponse(it) }
        return res
    }
    fun getProduct(productId: Long): Product {
        return productRepository.findById(productId).get()
    }
}