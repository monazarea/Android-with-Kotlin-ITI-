package com.example.coroutinelab1

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

suspend fun main():Unit = coroutineScope {
    val stateFlow = MutableStateFlow("")
    launch {
        stateFlow.collect {
            println(it)
        }
    }

    stateFlow.value = "1"
    stateFlow.value = "2"
    stateFlow.value = "3"
    stateFlow.value = "4"
    stateFlow.value = "5"
    stateFlow.value = "6"

    Thread.sleep(100)
}
