package com.hsproject.envmarket.service

import com.hsproject.envmarket.oauth.User
import com.hsproject.envmarket.orders.Order
import com.hsproject.envmarket.orders.OrderStatus
import com.hsproject.envmarket.repository.OrderRepository
import com.hsproject.envmarket.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

interface OrderService {
    fun createOrder(user: User, productList: List<Long>): Order
    fun getOrderById(id: Long): Order
    fun updateOrderStatus(id: Long, newStatus: OrderStatus): Order
}

@Service
class OrderServiceImpl(
        private val orderRepository: OrderRepository,
        private val userRepository: UserRepository
) : OrderService {

//    override fun createOrder(user: User, productList: List<Long>): Order {
//        val user = userService.getUserByEmail(user.email)
//        val newOrder = Order(
//                id = 0,
//                user = user,     // userId 대신 user 객체 할당
//                productList = productList,
//                createdAt = LocalDateTime.now(),
//                status = OrderStatus.PROCESSING
//        )
//        return orderRepository.save(newOrder)
//    }

    override fun createOrder(user: User, productList: List<Long>): Order {
        val savedUser = userRepository.save(user) // Save or update User
        val order = Order(
                id = 0,
                user = savedUser,
                productList = productList,
                createdAt = LocalDateTime.now(),
                status = OrderStatus.PROCESSING
        )
        return orderRepository.save(order)
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