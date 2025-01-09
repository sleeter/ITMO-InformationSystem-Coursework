package itmo.sleeter.infosys.repository

import itmo.sleeter.infosys.model.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository : JpaRepository<Role, Long> {
}