package itmo.sleeter.infosys.repository

import itmo.sleeter.infosys.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository :
    JpaRepository<User, Long>,
    PagingAndSortingRepository<User, Long>,
    JpaSpecificationExecutor<User> {
    fun findByLogin(login: String): Optional<User>
    fun existsByLogin(login: String): Boolean
    fun findAllByRoleIdAndDeletedFalse(roleId: Long, pageable: Pageable): Page<User>
}