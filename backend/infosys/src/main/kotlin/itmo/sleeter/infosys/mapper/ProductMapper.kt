package itmo.sleeter.infosys.mapper

import itmo.sleeter.infosys.dto.response.OrderedProductResponse
import itmo.sleeter.infosys.dto.response.ProductResponse
import itmo.sleeter.infosys.model.Product
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.springframework.stereotype.Component

@Mapper
@Component
interface ProductMapper {
    @Mapping(target = "category", source = "category.name")
    fun productToProductResponse(product: Product): ProductResponse

    fun productToOrderedProductResponse(product: ProductResponse, count: Int): OrderedProductResponse
}