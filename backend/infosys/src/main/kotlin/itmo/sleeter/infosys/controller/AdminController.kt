package itmo.sleeter.infosys.controller

import itmo.sleeter.infosys.dto.response.CompareUsersResponse
import itmo.sleeter.infosys.dto.response.UserResponse
import itmo.sleeter.infosys.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ROLE_admin')")
class AdminController(
    private val userService: UserService,
) {
    @GetMapping("/employee/edit")
    fun getUpdateEmployee(): ResponseEntity<List<CompareUsersResponse>> {
        return ResponseEntity.ok(userService.getUpdateUsers())
    }

    @PutMapping("/employee/edit/{id}")
    fun updateEmployee(@PathVariable id: Long, @RequestParam approved: Boolean): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.approveUpdate(id, approved))
    }
}