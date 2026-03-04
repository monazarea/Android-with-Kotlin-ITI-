package com.example.coroutinelab1

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take



    fun getEvenNmbers() = flow<Int> {
        for (i in 0 until 40 step 2){
            delay(1000)
            emit(i)
        }
    }

    suspend fun main(): Unit = coroutineScope {
        getEvenNmbers()
            .take(10)
            .collect {value ->
                println("The number is : $value")
            }
    }
