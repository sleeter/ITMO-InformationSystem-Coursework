package itmo.sleeter.infosys.controller

import itmo.sleeter.infosys.dto.request.UserRequest
import itmo.sleeter.infosys.dto.request.UserUpdateRequest
import itmo.sleeter.infosys.dto.response.UserResponse
import itmo.sleeter.infosys.service.UserService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/employee")
class UserController(
    private val userService: UserService
) {
    @GetMapping
    fun getEmployees(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
    ): ResponseEntity<Page<UserResponse>> {
        val pageable: Pageable = PageRequest.of(page, size)
        return ResponseEntity.ok(userService.getUsers(pageable))
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.getUserById(id))
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_admin')")
    fun addNewEmployee(@RequestBody @Valid req: UserRequest): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.createUser(req))
    }

    @PostMapping("/update/{id}")
    fun updateEmployee(@PathVariable id: Long, @RequestBody @Valid req: UserUpdateRequest): ResponseEntity<Void> {
        userService.updateUser(id, req)
        return ResponseEntity.ok().body(null)
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_admin')")
    fun deleteEmployee(@PathVariable id: Long): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.deleteUser(id))
    }
}