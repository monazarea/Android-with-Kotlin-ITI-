package com.example.lab1
import java.util.Random

fun main(){
   //welcome()
  // getNumberLessThan10()
   //calculate()
    //getFullName()
    getShape()

}

fun welcome(){
    println("Enter Your name:\n")
    val input = readLine()
    val name = if(input.isNullOrBlank())"Guest" else input
    println("Welcome $name")
}

fun getNumberLessThan10(){
    val arr = IntArray(100)
    for(i in arr.indices)
        arr[i]= Random().nextInt(100)+1;
    println("Numbers less than or equal 10:")

    for (number in arr)
        if(number<=10)
            println(number)
}

fun calculate() {
    val num1 = getValidNumber("Enter your first number:")
    val num2 = getValidNumber("Enter your second number:")
    val operation = getValidOperation()

    val result = when (operation) {
        "+" -> num1 + num2
        "-" -> num1 - num2
        "*" -> num1 * num2
        "/" -> if (num2 != 0.0) num1 / num2 else {
            println("Cannot divide by zero!")
            return
        }
        else -> return
    }

    println("$num1 $operation $num2 = $result")
}

fun getValidNumber(prompt: String): Double {
    while (true) {
        println(prompt)
        val input = readlnOrNull()
        if (!input.isNullOrBlank()) {
            val number = input.toDoubleOrNull()
            if (number != null) return number
        }
        println("Please enter a valid number!")
    }
}

fun getValidOperation(): String {
    val validOps = setOf("+", "-", "*", "/")
    while (true) {
        println("Choose your operation: + - * /")
        val op = readlnOrNull()
        if (op in validOps) return op!!
        println("Please enter a valid operation!")
    }
}

fun getFullName(){
    val firts = getvalidName("Enter your first name:")

    val second = getvalidName("Enter your second name:")

    println("Full name : $firts $second")

}
fun getvalidName(prompt: String):String{
    while (true){
        println(prompt)
        val name = readLine()
        if(!name.isNullOrBlank()) return name
    }
}

fun getShape(){
    val rows = 6

    for (i in 1..rows) {
        repeat(i) {
            print("*")
        }

        repeat(10 - i) {
            print(" ")
        }

        repeat(rows - i) {
            print(" ")
        }

        repeat(i){
            print("* ")
        }


        println()
    }
}


