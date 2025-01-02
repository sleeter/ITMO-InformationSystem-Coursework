package itmo.sleeter.infosys.service

import itmo.sleeter.infosys.dto.request.LoginRequest
import itmo.sleeter.infosys.dto.request.RegisterRequest
import itmo.sleeter.infosys.dto.response.TokenResponse
import itmo.sleeter.infosys.mapper.UserMapper
import itmo.sleeter.infosys.model.User
import itmo.sleeter.infosys.repository.RoleRepository
import itmo.sleeter.infosys.repository.UserRepository
import jakarta.persistence.EntityExistsException
import jakarta.persistence.EntityNotFoundException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.lang.Long.valueOf

val ADMIN_ID = valueOf(0)
val EMPLOYEE_ID = valueOf(1)


@Service
class UserService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val userMapper: UserMapper,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager
) {
    fun getUser(id: Long) : User {
        val user = userRepository.findById(id).orElseThrow {
            EntityNotFoundException("User with id=$id not found")
        }
        return user
    }
    fun register(req: RegisterRequest) : TokenResponse {
        val user = User()
        user.login = req.login
        user.setPassword(passwordEncoder.encode(req.password))
        user.role = if (req.role == "admin") roleRepository.findById(ADMIN_ID).get() else roleRepository.findById(EMPLOYEE_ID).get()
        user.name = req.name
        // TODO: pick_up_point_id
        user.deleted = req.deleted
        createUser(user)
        val jwt = jwtService.generateToken(user)
        return TokenResponse(jwt)
    }
    fun login(req: LoginRequest) : TokenResponse {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(
            req.login,
            req.password
        ))
        val user = getUserByLogin(req.login)
        val jwt = jwtService.generateToken(user)
        return TokenResponse(jwt)
    }

    fun userSave(user: User) : User = userRepository.save(user)
    fun createUser(user: User) : User {
        if (userRepository.existsByLogin(user.login!!)) {
            throw EntityExistsException("User with login=${user.login} already exists")
        }
        return userSave(user)
    }
    fun getUserByLogin(login: String) : User = userRepository.findByLogin(login).orElseThrow {
        throw EntityNotFoundException("User with login=$login not found")
    }
    fun getCurrentUser(): User {
        val username = SecurityContextHolder.getContext().authentication.name
        return getUserByLogin(username)
    }
}