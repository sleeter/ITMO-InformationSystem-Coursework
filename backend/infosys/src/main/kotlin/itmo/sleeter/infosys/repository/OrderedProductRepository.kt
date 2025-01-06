package itmo.sleeter.infosys.repository

import itmo.sleeter.infosys.model.OrderedProduct
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderedProductRepository : JpaRepository<OrderedProduct, Long> {
}