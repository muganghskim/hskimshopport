package com.hsproject.envmarket.oauth


import com.fasterxml.jackson.annotation.JsonManagedReference
import com.hsproject.envmarket.orders.Order
import com.hsproject.envmarket.products.CartProduct
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @Column(nullable = false, unique = true)
        val email: String,

        @Column(nullable = false)
        val password: String,

        @Column(name = "user_name", nullable = false)
        val userName: String,

        @JsonManagedReference
        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(
                name = "user_roles",
                joinColumns = [JoinColumn(name = "user_id")],
                inverseJoinColumns = [JoinColumn(name = "role_id")]
        )
        val roles: MutableSet<Role> = HashSet(),

        @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
        val order: MutableList<Order> = mutableListOf(),

        @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
        val cart: MutableList<CartProduct> = mutableListOf()
){
    //추가 메서드 구현 가능
}
