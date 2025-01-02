package itmo.sleeter.infosys.model

import itmo.sleeter.infosys.enumeration.Role
import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(name = "employees")
class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employees_id_gen")
    @SequenceGenerator(name = "employees_id_gen", sequenceName = "employees_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @Size(max = 30)
    @NotNull
    @Column(name = "name", nullable = false, length = 30)
    var name: String? = null

    @Size(max = 30)
    @NotNull
    @Column(name = "login", nullable = false, length = 30)
    var login: String? = null

    @Size(max = 255)
    @NotNull
    @Column(name = "password", nullable = false)
    var password: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pickup_points_id")
    var pickupPoints: PickupPoint? = null

    @NotNull
    @Column(name = "deleted", nullable = false)
    var deleted: Boolean? = false

    @Size(max = 50)
    @NotNull
    @Column(name = "role", nullable = false, length = 50)
    var role: String? = null
}