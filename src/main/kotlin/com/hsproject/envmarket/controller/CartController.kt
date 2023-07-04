package com.hsproject.envmarket.controller

import com.hsproject.envmarket.products.CartProduct
import com.hsproject.envmarket.service.CartService
import com.hsproject.envmarket.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/api/cart")
class CartController(private val cartService: CartService, private val userService: UserService) {
    @PostMapping("/{productId}")
    fun addItem(@PathVariable productId: String, @RequestParam quantity: Int = 1, @RequestParam email:String) {
        val user = userService.getUserByEmail(email)
        cartService.addItem(user, productId, quantity)
    }


    @GetMapping
    fun getItems(): List<CartProduct> {
        return cartService.getItems()
    }

    @PutMapping("/{productId}")
    fun updateQuantity(@PathVariable productId: String, @RequestParam quantity: Int) {
        cartService.updateQuantity(productId, quantity)
    }

    @DeleteMapping("/{productId}")
    fun removeItem(@PathVariable productId: String) {
        cartService.removeItem(productId)
    }
}