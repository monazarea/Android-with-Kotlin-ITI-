package com.example.roomcoroutinelab5

import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val repository = ProductRepository(db.productDao(), RetrofitInstance.api)

    private val _products = mutableStateOf<List<Product>>(emptyList())
    //val products: State<List<Product>> = _products

    val products: StateFlow<List<Product>> = repository.allProducts
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun fetchData(context: Context) {
        viewModelScope.launch {
            if (isNetworkAvailable(context)) {
                repository.refreshProducts()
            }
            //_products.value = repository.getStoredProducts()
        }
    }
}