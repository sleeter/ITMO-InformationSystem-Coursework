package itmo.sleeter.infosys.repository

import itmo.sleeter.infosys.model.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository :
    JpaRepository<Customer, Long>,
    PagingAndSortingRepository<Customer, Long>,
    JpaSpecificationExecutor<Customer> {
}