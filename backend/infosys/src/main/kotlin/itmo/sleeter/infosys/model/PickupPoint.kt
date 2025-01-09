package itmo.sleeter.infosys.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.annotations.ColumnDefault

@Entity
@Table(name = "pickup_points")
class PickupPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @Size(max = 50)
    @NotNull
    @Column(name = "address", nullable = false, length = 50)
    var address: String? = null

    @ColumnDefault("0")
    @Column(name = "size")
    var size: Long? = null

    @OneToMany(mappedBy = "pickupPoint")
    var orders: MutableSet<Order> = mutableSetOf()

    @OneToMany(mappedBy = "pickupPoint")
    var users: MutableSet<User> = mutableSetOf()
}