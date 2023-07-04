package com.hsproject.envmarket.repository

import com.hsproject.envmarket.products.CartProduct
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartRepository : JpaRepository<CartProduct, String> {
}