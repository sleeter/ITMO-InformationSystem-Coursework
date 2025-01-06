package itmo.sleeter.infosys.controller

import itmo.sleeter.infosys.dto.request.RegisterRequest
import itmo.sleeter.infosys.dto.response.UserCreateResponse
import itmo.sleeter.infosys.service.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/admin")
class AdminController(
    private val userService: UserService,
) {

    @PostMapping("/new-employee")
    @PreAuthorize("hasRole('ROLE_admin')")
    fun addNewEmployee(@RequestBody @Valid req: RegisterRequest): ResponseEntity<UserCreateResponse> {
        return ResponseEntity.ok(userService.createUser(req))
    }
}