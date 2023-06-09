package com.hsproject.envmarket.repository

import com.hsproject.envmarket.orders.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Order, Long> {
}