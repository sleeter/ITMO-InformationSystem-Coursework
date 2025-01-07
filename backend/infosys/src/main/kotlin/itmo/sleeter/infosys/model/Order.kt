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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_id_gen")
    @SequenceGenerator(name = "orders_id_gen", sequenceName = "orders_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customers_id")
    var customers: Customer? = null

    @NotNull
    @Column(name = "size", nullable = false)
    var size: Long? = null

    @NotNull
    @Column(name = "date", nullable = false)
    var date: Instant? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id")
    var status: Status? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pickup_points_id")
    var pickupPoint: PickupPoint? = null

    @NotNull
    @Column(name = "total_price", nullable = false)
    var totalPrice: BigDecimal? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_id")
    var payments: Payment? = null

    @OneToMany(mappedBy = "order")
    var orderedProducts: MutableSet<OrderedProduct> = mutableSetOf()
}