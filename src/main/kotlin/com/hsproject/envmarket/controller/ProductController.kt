package com.hsproject.envmarket.controller

import com.hsproject.envmarket.products.Product
import com.hsproject.envmarket.service.ProductService
import com.hsproject.envmarket.service.StorageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/products")
class ProductController(private val productService: ProductService, private val storageService: StorageService) {

    //전체 조회
    @GetMapping
    fun getAllProducts(): ResponseEntity<List<Product>> {
        val products = productService.getAllProducts()
        return ResponseEntity.ok(products)
    }

    //하나 조회
    @GetMapping("/{productId}")
    fun getProductById(@PathVariable productId: Long): ResponseEntity<Product> {
        val product = productService.getProductById(productId)
        return ResponseEntity.ok(product)
    }

    //상품 등록
    @PostMapping("/enroll")
    fun productEnroll(@RequestBody product: Product): ResponseEntity<Product> {
        val savedProduct = productService.insertProduct(product)
        return ResponseEntity.ok(savedProduct)
    }

    //업로드
    @PostMapping("/{productId}/image")
    fun uploadImage(
            @PathVariable productId: Long,
            @RequestParam("file") file: MultipartFile
    ): ResponseEntity<Product> {
        val product = productService.getProductById(productId)
        val imageUrl = storageService.storeFile(file)
        val updatedProduct = product.copy(imageUrl = imageUrl)
        productService.updateProduct(updatedProduct)
        return ResponseEntity.ok(updatedProduct)
    }
}
