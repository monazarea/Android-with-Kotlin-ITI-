package com.example.roomcoroutinelab5

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import coil.compose.AsyncImage
import com.example.roomcoroutinelab5.ui.theme.RoomCoroutineLab5Theme
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable object MainRoute
@Serializable data class DetailsRoute(val id: Int, val title: String, val description: String, val price: Double, val thumbnail: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val viewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        lifecycleScope.launch {
            viewModel.fetchData(this@MainActivity)
        }

        setContent {
            RoomCoroutineLab5Theme {
                val navController = rememberNavController()
                //val products by viewModel.products
                val products by viewModel.products.collectAsStateWithLifecycle()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = MainRoute,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<MainRoute> {
                            MainScreen(
                                products = products,
                                onProductNavigate = { product ->
                                    navController.navigate(DetailsRoute(
                                        product.id, product.title, product.description, product.price, product.thumbnail
                                    ))
                                }
                            )
                        }
                        composable<DetailsRoute> { backStackEntry ->
                            val route = backStackEntry.toRoute<DetailsRoute>()
                            DetailsScreen(route) { navController.popBackStack() }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    products: List<Product>,
    onProductNavigate: (Product) -> Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    if (isLandscape) {
        Row(modifier = Modifier.fillMaxSize()) {
            ProductList(products, modifier = Modifier.weight(0.4f)) {
                selectedProduct = it
            }
            VerticalDivider()
            Box(modifier = Modifier.weight(0.6f), contentAlignment = Alignment.Center) {
                selectedProduct?.let {
                    DetailsContent(it)
                } ?: Text("Select a product to see details")
            }
        }
    } else {
        ProductList(products) { product ->
            onProductNavigate(product)
        }
    }
}

@Composable
fun DetailsScreen(data: DetailsRoute, onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = onBack, modifier = Modifier.padding(8.dp)) { Text("Back") }
        val product = Product(data.id, data.title, data.description, data.price, data.thumbnail, "")
        DetailsContent(product)
    }
}

@Composable
fun DetailsContent(product: Product) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(model = product.thumbnail, contentDescription = null, modifier = Modifier.size(250.dp))
        Spacer(Modifier.height(16.dp))
        Text(text = product.title, style = MaterialTheme.typography.headlineMedium)
        Text(text = "Price: $${product.price}", color = Color(0xFF4CAF50), style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Text(text = product.description, style = MaterialTheme.typography.bodyLarge)
    }
}