package itmo.sleeter.infosys.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
@Table(name = "category")
class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_id_gen")
    @SequenceGenerator(name = "category_id_gen", sequenceName = "category_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @Size(max = 30)
    @NotNull
    @Column(name = "name", nullable = false, length = 30)
    var name: String? = null

    @Size(max = 255)
    @NotNull
    @Column(name = "description", nullable = false)
    var description: String? = null

    @OneToMany(mappedBy = "category")
    var products: MutableSet<Product> = mutableSetOf()
}