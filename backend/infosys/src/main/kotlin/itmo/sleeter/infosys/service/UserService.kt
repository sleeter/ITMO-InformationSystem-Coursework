package itmo.sleeter.infosys.service

import itmo.sleeter.infosys.dto.request.LoginRequest
import itmo.sleeter.infosys.dto.request.UserRequest
import itmo.sleeter.infosys.dto.response.CompareUsersResponse
import itmo.sleeter.infosys.dto.response.TokenResponse
import itmo.sleeter.infosys.dto.response.UserResponse
import itmo.sleeter.infosys.mapper.UserMapper
import itmo.sleeter.infosys.model.User
import itmo.sleeter.infosys.model.UserUpdate
import itmo.sleeter.infosys.repository.RoleRepository
import itmo.sleeter.infosys.repository.UserRepository
import itmo.sleeter.infosys.repository.UserUpdateRepository
import jakarta.persistence.EntityExistsException
import jakarta.persistence.EntityNotFoundException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.lang.Long.valueOf

@Service
class UserService(
    private val userRepository: UserRepository,
    private val userUpdateRepository: UserUpdateRepository,
    private val roleRepository: RoleRepository,
    private val pickupPointService: PickupPointService,
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
    fun register(req: UserRequest) : TokenResponse {
        val user = User()
        user.login = req.login
        user.setPassword(passwordEncoder.encode(req.password))
        user.role = if (req.role == "admin") roleRepository.findById(0).get() else roleRepository.findById(1).get()
        user.name = req.name
        user.pickupPoint = if (req.pickUpPointId != null) pickupPointService.getPickupPointById(req.pickUpPointId) else null
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
    fun createUser(req: UserRequest) : UserResponse {
        val user = User()
        user.login = req.login
        user.setPassword(passwordEncoder.encode(req.password))
        user.role = if (req.role == "admin") roleRepository.findById(0).get() else roleRepository.findById(1).get()
        user.name = req.name
        user.pickupPoint = if (req.pickUpPointId != null) pickupPointService.getPickupPointById(req.pickUpPointId) else null
        user.deleted = req.deleted
        createUser(user)
        return userMapper.userToUserResponse(user)
    }
    fun getUsers(): List<UserResponse> {
        return userRepository.findAll().filter { it -> it.role?.id!! == valueOf(1) && !it.deleted!! }.map { it -> userMapper.userToUserResponse(it) }
    }
    fun updateUser(id: Long, req: UserRequest) {
        val updateUser = UserUpdate()
        updateUser.id = id
        updateUser.login = req.login
        updateUser.password = passwordEncoder.encode(req.password)
        updateUser.name = req.name
        updateUser.role = if (req.role == "admin") roleRepository.findById(0).get() else roleRepository.findById(1).get()
        updateUser.pickupPoint = if (req.pickUpPointId != null) pickupPointService.getPickupPointById(req.pickUpPointId) else null
        updateUser.approved = "not approved"
        userUpdateRepository.save(updateUser)
    }
    fun approveUpdate(id: Long, approve: Boolean): UserResponse {
        val updateUser = userUpdateRepository.findById(id).get()
        val user = userRepository.findById(id).get()
        if (approve) {
            updateUser.approved = "approved"
            user.name = updateUser.name
            user.pickupPoint = updateUser.pickupPoint
            user.role = updateUser.role
            user.login = updateUser.login
            user.setPassword(updateUser.password!!)
        } else {
            updateUser.approved = "declined"
        }
        userRepository.save(user)
        userUpdateRepository.save(updateUser)
        return userMapper.userToUserResponse(user)
    }
    fun deleteUser(id: Long): UserResponse {
        val user = getUser(id)
        user.deleted = true
        return userMapper.userToUserResponse(userRepository.save(user))
    }
    fun getUpdateUsers(): List<CompareUsersResponse> {
        val updates = userUpdateRepository.findAll().filter { it -> it.approved!! == "not approved" }
        val users = userRepository.findAll().filter { it -> it.role?.id!! == valueOf(1) && !it.deleted!! }
        val userIds = users.map { it.id }.toSet()
        val filteredUpdates = updates.filter { it.id in userIds }

        if (users.isEmpty() || filteredUpdates.isEmpty()) {
            return emptyList()
        }

        val answ = users.map { it1 -> CompareUsersResponse(userMapper.userToUserResponse(it1) , userMapper.userToUserUpdateResponse(filteredUpdates.find { it2 -> it1.id == it2.id }!!)) }

        return answ
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