package itmo.sleeter.infosys.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull

@Entity
@Table(name = "ordered_products")
class OrderedProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ordered_products_id_gen")
    @SequenceGenerator(name = "ordered_products_id_gen", sequenceName = "ordered_products_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    var order: Order? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    var product: Product? = null

    @NotNull
    @Column(name = "count", nullable = false)
    var count: Int? = null
}