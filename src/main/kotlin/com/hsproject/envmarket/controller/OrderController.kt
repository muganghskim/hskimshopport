package com.hsproject.envmarket.controller

import com.hsproject.envmarket.orders.Order
import com.hsproject.envmarket.orders.OrderStatus
import com.hsproject.envmarket.service.OrderService
import com.hsproject.envmarket.service.UserService
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Slf4j
@RestController
@RequestMapping("/api/orders")
@CrossOrigin("*")
class OrderController(private val orderService: OrderService, private val userService: UserService) {

    @PostMapping
    fun createOrder(@RequestBody orderRequest: NewOrderRequest): ResponseEntity<Order> {
        val email = orderRequest.email
        val user = userService.getUserByEmail(email)
        val productList = orderRequest.productList
        val newOrder = orderService.createOrder(user, productList)
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder)
    }

    @GetMapping("/{id}")
    fun getOrderById(@PathVariable id: Long): ResponseEntity<Order> {
        val order = orderService.getOrderById(id)
        return ResponseEntity.ok(order)
    }

    @PatchMapping("/{id}/status")
    fun updateOrderStatus(
            @PathVariable id: Long,
            @RequestParam("status") newStatus: OrderStatus
    ): ResponseEntity<Order> {
        val updatedOrder = orderService.updateOrderStatus(id, newStatus)
        return ResponseEntity.ok(updatedOrder)
    }
}

// 주문 요청용 클래스 추가
data class NewOrderRequest(
        val email: String, // Added email field
        val productList: List<Long>
)
