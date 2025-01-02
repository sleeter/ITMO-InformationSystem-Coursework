package itmo.sleeter.infosys.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(name = "status")
class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "status_id_gen")
    @SequenceGenerator(name = "status_id_gen", sequenceName = "status_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @Size(max = 30)
    @NotNull
    @Column(name = "name", nullable = false, length = 30)
    var name: String? = null

    @OneToMany(mappedBy = "status")
    var orders: MutableSet<Order> = mutableSetOf()
}