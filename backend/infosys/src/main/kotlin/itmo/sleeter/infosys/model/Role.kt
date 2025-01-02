package itmo.sleeter.infosys.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(name = "roles")
class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_id_gen")
    @SequenceGenerator(name = "roles_id_gen", sequenceName = "roles_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @Size(max = 30)
    @NotNull
    @Column(name = "name", nullable = false, length = 30)
    var name: String? = null

    @OneToMany(mappedBy = "role")
    var users: MutableSet<User> = mutableSetOf()
}