package com.example.coroutinelab1

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.coroutineContext

//fun main(): Unit = runBlocking {
//    val flow = flowOf(1,2,3)
//
//    val flow2 = flow{
//        repeat(10){i->
//            emit(i)
//        }
//    }
//
//    launch {
//        flow
//            .flowOn(Dispatchers.Default)
//            .map { item -> item*2 }
//           // .flowOn(Dispatchers.Main)
//            .take(2)
//            .flowOn(Dispatchers.IO)
//            .filter { item -> item <5 }
//            .collect { item ->
//                println(item)
//            }
//    }
//}

fun getNmbers() = flow<Int> {
    for (i in 1..10){
        delay(1000)
        emit(i)
    }
}

suspend fun main(): Unit = coroutineScope {
    getNmbers()
        .filter { item -> item > 5 }
        .map{item -> item * 2}
        .collect {
            println("The number is : $it")
        }
}