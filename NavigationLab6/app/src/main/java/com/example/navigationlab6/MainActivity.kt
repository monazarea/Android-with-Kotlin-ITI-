package com.example.navigationlab6

import android.os.Bundle
import android.telecom.Call
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.navigationlab6.ui.theme.NavigationLab6Theme

class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            navController = rememberNavController()
            NavigationLab6Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SetupNavGraph(navController, Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun SetupNavGraph(navController: NavHostController,modifier: Modifier){
    NavHost(
        navController = navController,
        startDestination = Screens.SignUpScreen,
    ){
        composable < Screens.SignUpScreen>{
            SignUpScreen {
                signinScreen -> navController.navigate(signinScreen)
            }
        }

        composable<Screens.SigninScreen> { backStackEntry ->
            val data = backStackEntry.toRoute<Screens.SigninScreen>()
            SigninScreen(registeredData = data) { homeData ->
                navController.navigate(homeData)
            }
        }

        composable<Screens.HomeScreen> { backStackEntry ->
            val data = backStackEntry.toRoute<Screens.HomeScreen>()

            HomeScreen(userName = data.userName) {
                navController.navigate(Screens.SignUpScreen) {
                    popUpTo(Screens.SignUpScreen) {
                        inclusive = true
                    }
                }
            }
        }



    }
}





//@Composable
//fun SetupNavGraph(navController: NavHostController,modifier: Modifier){
//    NavHost(
//        navController = navController,
//        startDestination = Screens.HomeScreen
//    ){
//        composable <Screens.HomeScreen> {
//            HomeScreen {
//                detailsScreen -> navController.navigate(detailsScreen)
//            }
//        }
//        composable < Screens.DetailsScreen>{ backStackEntry->
//            val details: Screens.DetailsScreen = backStackEntry.toRoute<Screens.DetailsScreen>()
//            DetailsScreen(details) {
//                navController.popBackStack()
//            }
//
//        }
//    }
//}
//
//@Composable
//fun HomeScreen(
//    modifier: Modifier = Modifier,
//    onOpenDetailsClicked:(Screens.DetailsScreen)-> Unit
//) {
//    Column(
//        modifier = modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(text = "Home Screen")
//        Button(
//            onClick = {
//                onOpenDetailsClicked(Screens.DetailsScreen("Hello from Home","01023486493"))
//            }
//        ) {
//            Text(text = "Open Details")
//        }
//    }
//}
//
//@Composable
//fun DetailsScreen(
//    details: Screens.DetailsScreen,
//    modifier: Modifier= Modifier,
//    onBackClicked:()-> Unit
//){
//    Column(
//        modifier = modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(text = "Message= ${details.message}")
//        Spacer(Modifier.height(16.dp))
//        Text(text = "Phone= ${details.phone}")
//        Button(
//            onClick = {
//                onBackClicked()
//            }
//        ) {
//            Text(text = "back")
//        }
//    }
//}

