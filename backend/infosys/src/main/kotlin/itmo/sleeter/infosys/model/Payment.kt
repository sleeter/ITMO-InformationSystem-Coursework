package itmo.sleeter.infosys.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(name = "payments")
class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payments_id_gen")
    @SequenceGenerator(name = "payments_id_gen", sequenceName = "payments_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @Size(max = 255)
    @NotNull
    @Column(name = "payments_method", nullable = false)
    var paymentsMethod: String? = null

    @OneToMany(mappedBy = "payments")
    var orders: MutableSet<Order> = mutableSetOf()
}