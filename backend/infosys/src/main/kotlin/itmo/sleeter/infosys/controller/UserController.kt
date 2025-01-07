package itmo.sleeter.infosys.controller

import itmo.sleeter.infosys.dto.request.UserRequest
import itmo.sleeter.infosys.dto.response.UserResponse
import itmo.sleeter.infosys.service.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/employee")
class UserController(
    private val userService: UserService
) {
    @GetMapping
    fun getEmployees(): ResponseEntity<List<UserResponse>> {
        return ResponseEntity.ok(userService.getUsers())
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_admin')")
    fun addNewEmployee(@RequestBody @Valid req: UserRequest): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.createUser(req))
    }

    @PostMapping("/update/{id}")
    fun updateEmployee(@PathVariable id: Long, @RequestBody @Valid req: UserRequest): ResponseEntity<Void> {
        userService.updateUser(id, req)
        return ResponseEntity.ok().body(null)
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_admin')")
    fun deleteEmployee(@PathVariable id: Long): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.deleteUser(id))
    }
}