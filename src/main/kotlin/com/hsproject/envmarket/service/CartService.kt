package com.hsproject.envmarket.service

import com.hsproject.envmarket.oauth.User
import com.hsproject.envmarket.products.CartProduct
import com.hsproject.envmarket.repository.CartRepository
import com.hsproject.envmarket.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class CartService(private val cartRepository: CartRepository, private val userRepository: UserRepository) {
    fun addItem(user: User, productId: String, quantity: Int = 1) {
        val item = user.cart.find { it.productId == productId }
        if (item != null) {
            item.quantity += quantity
        } else {
            user.cart.add(CartProduct(productId, quantity, user))
        }
        userRepository.save(user)
    }

    fun getItems(): List<CartProduct> {
        return cartRepository.findAll()
    }

    fun updateQuantity(productId: String, quantity: Int) {
        val item = cartRepository.findById(productId)
        if (item.isPresent) {
            item.get().quantity = quantity
            cartRepository.save(item.get())
        }
    }

    fun removeItem(productId: String) {
        cartRepository.deleteById(productId)
    }
}