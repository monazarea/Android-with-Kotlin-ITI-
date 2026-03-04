package com.example.roomcoroutinelab5

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "products")
data class Product(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val thumbnail: String,
    val category: String
)


data class ProductResponse(
    val products: List<Product>
)