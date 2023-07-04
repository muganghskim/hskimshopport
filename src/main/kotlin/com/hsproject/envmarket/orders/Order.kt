package com.hsproject.envmarket.orders

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "orders")
data class Order(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,
        val userId: Long,
        @ElementCollection
        val productList: List<Long>,
        val createdAt: LocalDateTime,
        @Enumerated(EnumType.STRING)
        val status: OrderStatus
)

enum class OrderStatus {
    PROCESSING, SHIPPED, DELIVERED
}