package com.example.coroutinelab1

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

suspend fun main():Unit = coroutineScope {

    val sharedFlow = MutableSharedFlow<Int>()

    launch {
        sharedFlow.collect {
            println("first: $it")
        }
    }

    launch {
        sharedFlow.collect {
            println("second: $it")
        }
    }
    sharedFlow.emit(5)
    sharedFlow.emit(3)

    println("Done")
}

//suspend fun main(): Unit = coroutineScope {
//    val coroutineScope = CoroutineScope(Dispatchers.Default)
//
//    val sharedFlow = flow<Int>{
//        emit(5)
//        emit(7)
//        emit(9)
//    }.shareIn(coroutineScope, SharingStarted.Lazily)
//
//    launch {
//        sharedFlow.collect {
//            println("I'm converted flow: $it")
//        }
//    }
//}


