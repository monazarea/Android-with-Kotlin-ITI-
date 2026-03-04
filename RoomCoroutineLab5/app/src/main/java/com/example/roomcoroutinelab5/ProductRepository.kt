package com.example.roomcoroutinelab5

import kotlinx.coroutines.flow.Flow

class ProductRepository(private val productDao: ProductDao, private val apiService: ApiService) {

    suspend fun refreshProducts() {
        try {
            val response = apiService.getProducts()
            productDao.insertProducts(response.products)
        } catch (e: Exception) {
        }
    }
    val allProducts: Flow<List<Product>> = productDao.getAllProducts()
//    suspend fun getStoredProducts(): List<Product> {
//        return productDao.getAllProducts()
//    }
}