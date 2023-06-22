package com.hsproject.envmarket.service

import com.hsproject.envmarket.products.Product
import com.hsproject.envmarket.repository.ProductRepository
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun getAllProducts(): List<Product> = productRepository.findAll()

    fun getProductById(productId: Long): Product =
            productRepository.findById(productId).orElseThrow { EntityNotFoundException("Product not found") }

    fun getProductByName(productName: String): List<Product> {
        val products = productRepository.findByName(productName)
//        if (products.isNotEmpty()) {
//            return products
//        } else {
//            throw EntityNotFoundException("Product not found")
//        }
        return products
    }

    fun insertProduct(product: Product): Product {
        if (product.id == null || !productRepository.existsById(product.id)) {
            return productRepository.save(product)
        }
        throw Exception("Product already exist")
    }

    fun updateProduct(product: Product): Product {
        if (product.id == null || !productRepository.existsById(product.id)) {
            throw EntityNotFoundException("Product not found")
        }
        return productRepository.save(product)
    }
}
