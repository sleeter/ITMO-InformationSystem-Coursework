package itmo.sleeter.infosys.service

import itmo.sleeter.infosys.dto.response.ProductResponse
import itmo.sleeter.infosys.mapper.ProductMapper
import itmo.sleeter.infosys.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val productMapper: ProductMapper
) {
    fun getProducts(): List<ProductResponse> {
        return productRepository.findAll().map { productMapper.productToProductResponse(it) }
    }
}