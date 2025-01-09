package itmo.sleeter.infosys.service

import itmo.sleeter.infosys.dto.response.CategoryResponse
import itmo.sleeter.infosys.mapper.CategoryMapper
import itmo.sleeter.infosys.repository.CategoryRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository,
    private val categoryMapper: CategoryMapper
) {
    fun getCategories(pageable: Pageable): Page<CategoryResponse> {
        val page = categoryRepository.findAll(pageable).map { categoryMapper.categoryToCategoryResponse(it) }
        return PageImpl(page.content, pageable, page.totalElements)
    }
}