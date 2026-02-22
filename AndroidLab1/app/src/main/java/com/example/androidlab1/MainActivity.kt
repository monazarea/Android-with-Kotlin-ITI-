package com.example.androidlab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidlab1.ui.theme.AndroidLab1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidLab1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                }
            }
        }
    }
}

/*
data class
data collection
item widget
lazy column
 */

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListOfCakePreview(){
    val cakes = listOf(
        Cake(R.drawable.cake1,"Cake One","Cake One Description"),
        Cake(R.drawable.cake2,"Cake Two","Cake Two Description"),
        Cake(R.drawable.cake3,"Cake Three","Cake Three Description"),
        Cake(R.drawable.cake4,"Cake Four","Cake Four Description"),
        Cake(R.drawable.cake5,"Cake Five","Cake Five Description"),
        Cake(R.drawable.cake6,"Cake Six","Cake Six Description"),
        Cake(R.drawable.cake7,"Cake Seven","Cake Seven Description"),
        Cake(R.drawable.cake8,"Cake Eight","Cake Eight Description"),
        Cake(R.drawable.cake9,"Cake Nine","Cake Nine Description")
    )

    AndroidLab1Theme {
        ListOfCake(cakes)
    }
}

@Composable
fun ListOfCake(cakes: List<Cake>, modifier: Modifier= Modifier){
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(cakes.size){ index ->
            CakeRow(cakes[index])
        }
    }
}

@Composable
fun CakeRow(cake: Cake,modifier: Modifier = Modifier){
    Row(modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(cake.image),
            contentDescription = cake.name,
            contentScale = ContentScale.Crop,

            modifier = Modifier
                .size(108.dp)
                .clip(
                    RoundedCornerShape(16.dp),
                )
        )
        Column(
            modifier = Modifier
                .padding(start = 12.dp)

        ) {
            Text(
                text = cake.name,
                fontSize = 24.sp,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .wrapContentWidth()
            )
            Text(
                text = cake.description,
                fontSize = 16.sp,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .wrapContentWidth()
            )

        }

    }
}