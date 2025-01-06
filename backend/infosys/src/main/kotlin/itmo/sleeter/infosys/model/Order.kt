package itmo.sleeter.infosys.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.math.BigDecimal
import java.time.Instant

@Entity
@Table(name = "orders")
class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customers_id")
    var customers: Customer? = null

    @NotNull
    @Column(name = "size", nullable = false)
    var size: Long? = null

    @NotNull
    @Column(name = "date", nullable = false)
    var date: Instant? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    var status: Status? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pickup_points_id")
    var pickupPoint: PickupPoint? = null

    @NotNull
    @Column(name = "total_price", nullable = false)
    var totalPrice: BigDecimal? = null

    @Size(max = 255)
    @Column(name = "description")
    var description: String? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payments_id")
    var payments: Payment? = null

    @OneToMany(mappedBy = "order")
    var orderedProducts: MutableSet<OrderedProduct> = mutableSetOf()
}