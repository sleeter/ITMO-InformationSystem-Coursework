package itmo.sleeter.infosys.service

import itmo.sleeter.infosys.dto.request.LoginRequest
import itmo.sleeter.infosys.dto.request.UserRequest
import itmo.sleeter.infosys.dto.request.UserUpdateRequest
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
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
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
    fun getUsers(pageable: Pageable): Page<UserResponse> {
        val usersPage = userRepository.findAllByRoleIdAndDeletedFalse(1, pageable)
        val userResponses = usersPage.content.map { userMapper.userToUserResponse(it) }

        return PageImpl(userResponses, pageable, usersPage.totalElements)
    }
    fun getUserById(id: Long) : UserResponse {
        val user = userRepository.findById(id).orElseThrow {
            EntityNotFoundException("User with id=$id not found")
        }
        return userMapper.userToUserResponse(user)
    }
    fun updateUser(id: Long, req: UserUpdateRequest) {
        val updateUser = UserUpdate()
        updateUser.id = id
        updateUser.login = req.login
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
    fun getUpdateUsers(pageable: Pageable): Page<CompareUsersResponse> {
        val updates = userUpdateRepository.findAllByApproved("not approved", pageable)
        val users = userRepository.findAllByRoleIdAndDeletedFalse(1, pageable)

        if (users.isEmpty || updates.isEmpty) {
            return Page.empty()
        }

        val userIds = users.content.map { it.id }.toSet()
        val filteredUpdates = updates.content.filter { it.id in userIds }

        val responses = users.content.mapNotNull { user ->
            val update = filteredUpdates.find { it.id == user.id }
            update?.let {
                CompareUsersResponse(
                    userMapper.userToUserResponse(user),
                    userMapper.userToUserUpdateResponse(update)
                )
            }
        }

        return PageImpl(responses, pageable, users.totalElements)
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