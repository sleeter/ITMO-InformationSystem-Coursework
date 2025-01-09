package itmo.sleeter.infosys.repository

import itmo.sleeter.infosys.model.Payment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface PaymentRepository :
    JpaRepository<Payment, Long>,
    PagingAndSortingRepository<Payment, Long>,
    JpaSpecificationExecutor<Payment> {
}