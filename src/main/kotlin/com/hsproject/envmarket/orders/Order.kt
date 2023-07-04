package com.hsproject.envmarket.orders

import com.hsproject.envmarket.oauth.User
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "orders")
data class Order(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long,
        @ElementCollection
        val productList: List<Long>,
        val createdAt: LocalDateTime,
        @Enumerated(EnumType.STRING)
        val status: OrderStatus,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "ordermember_id")
        val user: User
)

enum class OrderStatus {
    PROCESSING, SHIPPED, DELIVERED
}