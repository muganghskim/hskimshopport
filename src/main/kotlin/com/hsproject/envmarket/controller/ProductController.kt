package com.hsproject.envmarket.controller

import com.hsproject.envmarket.products.Product
import com.hsproject.envmarket.service.ProductService
import com.hsproject.envmarket.service.StorageService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/products")
@CrossOrigin("*")
class ProductController(private val productService: ProductService, private val storageService: StorageService) {

    //상품 전체 조회
    @GetMapping
    fun getAllProducts(): ResponseEntity<List<Product>> {
        val products = productService.getAllProducts()
        return ResponseEntity.ok(products)
    }

    // 상품 하나 조회
    @GetMapping("/{productId}")
    fun getProductById(@PathVariable productId: Long): ResponseEntity<Product> {
        val product = productService.getProductById(productId)
        return ResponseEntity.ok(product)
    }

    // 상품 이름으로 검색
    @GetMapping("/search")
    fun getProductByName(@RequestParam searchName: String): ResponseEntity<List<Product>> {
        val searchProduct = productService.getProductByName(searchName)
        return if (searchProduct.isNotEmpty()) {
            ResponseEntity.ok(searchProduct)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(emptyList())
        }
    }

    //상품 등록
    @PostMapping("/enroll")
    fun productEnroll(
            @RequestParam("file") file: MultipartFile,
            @RequestParam("name") name: String,
            @RequestParam("description") description: String,
            @RequestParam("price") price: Float
    ): ResponseEntity<Product> {
        val imageUrl = storageService.storeFile(file)
        val product = Product(name = name, description = description, price = price, imageUrl = imageUrl)
        val savedProduct = productService.insertProduct(product)
        return ResponseEntity.ok(savedProduct)
    }

    //상품 이미지 업데이트
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
