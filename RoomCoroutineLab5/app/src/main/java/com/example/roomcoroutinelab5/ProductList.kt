package com.example.roomcoroutinelab5

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ProductList(
    products: List<Product>,
    modifier: Modifier = Modifier,
    onProductClick: (Product) -> Unit
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(products) { product ->
            ProductItem(product = product) {
                onProductClick(product)
            }
        }
    }
}
