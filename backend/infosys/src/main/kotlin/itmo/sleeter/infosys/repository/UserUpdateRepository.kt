package itmo.sleeter.infosys.repository

import itmo.sleeter.infosys.model.UserUpdate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserUpdateRepository : JpaRepository<UserUpdate, Long> {
}