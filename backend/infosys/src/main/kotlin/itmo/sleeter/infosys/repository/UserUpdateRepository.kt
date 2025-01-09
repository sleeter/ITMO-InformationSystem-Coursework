package itmo.sleeter.infosys.repository

import itmo.sleeter.infosys.model.UserUpdate
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface UserUpdateRepository :
    JpaRepository<UserUpdate, Long>,
    PagingAndSortingRepository<UserUpdate, Long> {
    fun findAllByApproved(approved: String, pageable: Pageable): Page<UserUpdate>
}