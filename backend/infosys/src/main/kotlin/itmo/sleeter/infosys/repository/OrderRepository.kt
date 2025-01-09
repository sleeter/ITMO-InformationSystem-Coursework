package itmo.sleeter.infosys.repository

import itmo.sleeter.infosys.model.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository :
    JpaRepository<Order, Long>,
    PagingAndSortingRepository<Order, Long>,
    JpaSpecificationExecutor<Order> {
}