package itmo.sleeter.infosys.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(name = "customers")
class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customers_id_gen")
    @SequenceGenerator(name = "customers_id_gen", sequenceName = "customers_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @Size(max = 30)
    @NotNull
    @Column(name = "name", nullable = false, length = 30)
    var name: String? = null

    @Size(max = 50)
    @NotNull
    @Column(name = "email", nullable = false, length = 50)
    var email: String? = null

    @NotNull
    @Column(name = "age", nullable = false)
    var age: Int? = null

    @Size(max = 30)
    @NotNull
    @Column(name = "phone_number", nullable = false, length = 30)
    var phoneNumber: String? = null

    @OneToMany(mappedBy = "customers")
    var orders: MutableSet<Order> = mutableSetOf()
}