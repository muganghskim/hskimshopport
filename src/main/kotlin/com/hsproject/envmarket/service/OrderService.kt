package com.hsproject.envmarket.service

import com.hsproject.envmarket.orders.Order
import com.hsproject.envmarket.orders.OrderStatus
import com.hsproject.envmarket.repository.OrderRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

interface OrderService {
    fun createOrder(userId: Long, productList: List<Long>): Order
    fun getOrderById(id: Long): Order
    fun updateOrderStatus(id: Long, newStatus: OrderStatus): Order
}

@Service
class OrderServiceImpl(private val orderRepository: OrderRepository) : OrderService {
    override fun createOrder(userId: Long, productList: List<Long>): Order {
        val newOrder = Order(id = 0, userId = userId, productList = productList, createdAt = LocalDateTime.now(), status = OrderStatus.PROCESSING)
        return orderRepository.save(newOrder)
    }

    override fun getOrderById(id: Long): Order {
        return orderRepository.findById(id).orElseThrow { NoSuchElementException("Order not found with id: $id") }
    }

    override fun updateOrderStatus(id: Long, newStatus: OrderStatus): Order {
        val order = getOrderById(id)
        val updatedOrder = order.copy(status = newStatus)
        return orderRepository.save(updatedOrder)
    }
}