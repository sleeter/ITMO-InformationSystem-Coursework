package itmo.sleeter.infosys.mapper

import itmo.sleeter.infosys.dto.response.CategoryResponse
import itmo.sleeter.infosys.model.Category
import org.mapstruct.Mapper
import org.springframework.stereotype.Component

@Mapper
@Component
interface CategoryMapper {
    fun categoryToCategoryResponse(category: Category): CategoryResponse
}