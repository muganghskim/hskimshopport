package com.hsproject.envmarket.products

import javax.persistence.*

@Entity
@Table(name = "products")
data class Product(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        val name: String,
        val description: String,
        val price: Double,
        val imageUrl: String? = null
)
