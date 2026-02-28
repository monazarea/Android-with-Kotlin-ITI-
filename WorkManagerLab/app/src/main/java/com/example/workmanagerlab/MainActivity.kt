package com.example.workmanagerlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import coil3.compose.AsyncImage
import com.example.workmanagerlab.ui.theme.WorkManagerLabTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : ComponentActivity() {
    private val productsState = mutableStateOf<List<Product>>(emptyList())
    private val isLoading = mutableStateOf(true)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val request = OneTimeWorkRequestBuilder<ProductWorker>().build()
        val workManager = WorkManager.getInstance(this)
        workManager.enqueue(request)

        workManager.getWorkInfoByIdLiveData(request.id).observe(this) { workInfo ->
            if (workInfo != null) {
                when (workInfo.state) {
                    WorkInfo.State.SUCCEEDED -> {
                        val json = workInfo.outputData.getString("PRODUCTS_JSON")
                        if (json != null) {
                            val type = object : TypeToken<List<Product>>() {}.type
                            productsState.value = Gson().fromJson(json, type)
                            isLoading.value = false
                        }
                    }
                    WorkInfo.State.FAILED -> {
                        isLoading.value = false
                    }
                    else -> {}
                }
            }
        }
        setContent {
            WorkManagerLabTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (isLoading.value) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    } else {
                        ProductList(productsState.value, Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}

@Composable
fun ProductList(products: List<Product>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(products) { product ->
            ProductItem(product)
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = product.thumbnail,
                contentDescription = product.title,
                modifier = Modifier.size(80.dp)
            )

            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(text = product.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = "${product.price} $", color = Color.Blue)
            }
        }
    }
}

