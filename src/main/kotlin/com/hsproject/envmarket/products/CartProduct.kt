package com.hsproject.envmarket.products

import com.hsproject.envmarket.oauth.User
import javax.persistence.*

@Entity
data class CartProduct(
        @Id
        val productId: String,
        var quantity: Int,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "cartmember_id")
        val user: User
)