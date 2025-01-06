package itmo.sleeter.infosys.controller

import itmo.sleeter.infosys.dto.response.ProductResponse
import itmo.sleeter.infosys.service.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/product")
class ProductController(
    private val productService: ProductService
) {
    @GetMapping
    fun getProducts() : ResponseEntity<List<ProductResponse>> {
        return ResponseEntity.ok(productService.getProducts())
    }
}