package itmo.sleeter.infosys.controller

import itmo.sleeter.infosys.dto.response.CompareUsersResponse
import itmo.sleeter.infosys.dto.response.UserResponse
import itmo.sleeter.infosys.service.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
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
    fun getUpdateEmployee(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
    ): ResponseEntity<Page<CompareUsersResponse>> {
        val pageable: Pageable = PageRequest.of(page, size)
        return ResponseEntity.ok(userService.getUpdateUsers(pageable))
    }

    @PutMapping("/employee/edit/{id}")
    fun updateEmployee(@PathVariable id: Long, @RequestParam approved: Boolean): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.approveUpdate(id, approved))
    }
}