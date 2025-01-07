package itmo.sleeter.infosys.service

import itmo.sleeter.infosys.dto.response.ProductResponse
import itmo.sleeter.infosys.mapper.ProductMapper
import itmo.sleeter.infosys.model.Product
import itmo.sleeter.infosys.repository.ProductRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val productMapper: ProductMapper
) {
    fun getProducts(pageable: Pageable): Page<ProductResponse> {
        val page = productRepository.findAll(pageable).map { productMapper.productToProductResponse(it) }
        return PageImpl(page.content, pageable, page.totalElements)
    }
    fun getProduct(productId: Long): Product {
        return productRepository.findById(productId).get()
    }
}