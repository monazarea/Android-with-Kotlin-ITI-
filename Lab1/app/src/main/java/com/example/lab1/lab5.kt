

fun calculateHOF(num1: Double, num2: Double, operation: (Double, Double) -> Double): Double {
    val result = operation(num1, num2)
    return result
}

fun add(a: Double, b: Double) = a + b
fun subtract(a: Double, b: Double) = a - b
fun multiply(a: Double, b: Double) = a * b
fun divide(a: Double, b: Double) = if (b != 0.0) a / b else Double.NaN

fun getValidOperationHOF(): (Double, Double) -> Double {
    val ops = mapOf(
        "+" to ::add,
        "-" to ::subtract,
        "*" to ::multiply,
        "/" to ::divide
    )

    while (true) {
        println("Choose your operation: + - * /")
        val opInput = readlnOrNull()
        if (opInput != null && ops.containsKey(opInput)) {
            return ops[opInput]!!
        }
        println("Please enter a valid operation!")
    }
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


fun main() {

    while (true) {
        val num1 = getValidNumber("Enter your first number:")
        val num2 = getValidNumber("Enter your second number:")
        val operation = getValidOperationHOF()

        val result = calculateHOF(num1, num2, operation)
        println("Result: $result")

        println("Do you want to perform another calculation? (yes/no)")
        val again = readlnOrNull()?.lowercase()
        if (again != "yes") break
    }

    println("Thank you for using HOF Calculator!")
}
