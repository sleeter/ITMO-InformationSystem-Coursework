package itmo.sleeter.infosys.repository

import itmo.sleeter.infosys.model.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository :
    JpaRepository<Category, Long>,
    PagingAndSortingRepository<Category, Long>,
    JpaSpecificationExecutor<Category> {
}