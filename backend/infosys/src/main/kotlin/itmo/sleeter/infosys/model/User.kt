package itmo.sleeter.infosys.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


@Entity
@Table(name = "users")
class User : UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_gen")
    @SequenceGenerator(name = "users_id_gen", sequenceName = "users_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @Size(max = 50)
    @NotNull
    @Column(name = "login", nullable = false, length = 50)
    var login: String? = null

    @Size(max = 100)
    @NotNull
    @Column(name = "password", nullable = false, length = 100)
    private var password: String? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    var role: Role? = null

    @Size(max = 30)
    @NotNull
    @Column(name = "name", nullable = false, length = 30)
    var name: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pickup_points_id")
    var pickupPoint: PickupPoint? = null

    @NotNull
    @Column(name = "deleted", nullable = false)
    var deleted: Boolean? = false

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority("ROLE_${role!!.name}"))
    }

    override fun getPassword(): String? {
        return password
    }
    fun setPassword(password: String) {
        this.password = password
    }

    override fun getUsername(): String? {
        return login
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}