package com.example.myapplication

class Complex(
    val realPart: Double = 0.0,
    val imaginaryPart: Double = 0.0
) {

    constructor(realPart: Double) : this(realPart, 0.0) {
        println("Created using secondary constructor")
    }

    operator fun plus(other: Complex): Complex {
        val newReal = realPart + other.realPart
        val newImaginary = imaginaryPart + other.imaginaryPart
        return Complex(newReal, newImaginary)
    }

    operator fun minus(other: Complex): Complex {
        val newReal = realPart - other.realPart
        val newImaginary = imaginaryPart - other.imaginaryPart
        return Complex(newReal, newImaginary)
    }
}

fun Complex.show(label: String = "Result") {
    println("$label: $realPart + ${imaginaryPart}i")
}

fun main() {

    val num1 = Complex(realPart = 5.0, imaginaryPart = 3.0)

    val num2 = Complex(realPart = 2.0)

    val num3 = Complex(4.0)

    val addition = num1 + num2
    val subtraction = num1 - num3

    addition.show()
    subtraction.show(label = "Difference")
}
