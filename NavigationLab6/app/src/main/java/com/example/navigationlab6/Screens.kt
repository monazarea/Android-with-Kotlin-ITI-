package com.example.navigationlab6

import android.os.Message
import kotlinx.serialization.Serializable

sealed class Screens {
//    @Serializable
//    object HomeScreen: Screens()
//
//    @Serializable
//    data class DetailsScreen(val message: String,val phone: String): Screens()


    @Serializable
    object SignUpScreen : Screens()

    @Serializable
    data class SigninScreen(val userName:String, val password : String ) : Screens()

    @Serializable
    data class HomeScreen(val userName : String): Screens()
}