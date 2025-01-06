package itmo.sleeter.infosys.service

import itmo.sleeter.infosys.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {
}