package itmo.sleeter.infosys.repository

import itmo.sleeter.infosys.model.Status
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface StatusRepository :
    JpaRepository<Status, Long>,
    PagingAndSortingRepository<Status, Long>,
    JpaSpecificationExecutor<Status> {
}