abstract class Shape {
    var dim: Double = 0.0
        set(value) {
            field = value
        }
        get() = field

    abstract fun calcArea(): Double
}

class Rectangle() : Shape() {

    var width: Double = 0.0
        set(value) {
            field = value
        }
        get() = field

    var height: Double = 0.0
        set(value) {
            field = value
        }
        get() = field

    constructor(w: Double, h: Double) : this() {
        width = w
        height = h
    }

    override fun calcArea(): Double {
        return width * height
    }
}

class Circle() : Shape() {

    var radius: Double = 0.0
        set(value) {
            field = value
        }
        get() = field

    constructor(r: Double) : this() {
        radius = r
    }

    override fun calcArea(): Double {
        return Math.PI * radius * radius
    }
}

class Triangle() : Shape() {

    var base: Double = 0.0
        set(value) {
            field = value
        }
        get() = field

    var height: Double = 0.0
        set(value) {
            field = value
        }
        get() = field

    constructor(b: Double, h: Double) : this() {
        base = b
        height = h
    }

    override fun calcArea(): Double {
        return 0.5 * base * height
    }
}

class Picture {

    fun sumAreas(shape1: Shape, shape2: Shape, shape3: Shape): Double {
        return shape1.calcArea() + shape2.calcArea() + shape3.calcArea()
    }
}
fun main() {
    println("=== Rectangle ===")
    print("Enter rectangle width: ")
    val rectWidth = readln().toDouble()
    print("Enter rectangle height: ")
    val rectHeight = readln().toDouble()
    val rectangle = Rectangle(rectWidth, rectHeight)

    println("\n=== Circle ===")
    print("Enter circle radius: ")
    val circRadius = readln().toDouble()
    val circle = Circle(circRadius)

    println("\n=== Triangle ===")
    print("Enter triangle base: ")
    val triBase = readln().toDouble()
    print("Enter triangle height: ")
    val triHeight = readln().toDouble()
    val triangle = Triangle(triBase, triHeight)

    println("\n=== Results ===")
    println("Rectangle area: ${rectangle.calcArea()}")
    println("Circle area: ${circle.calcArea()}")
    println("Triangle area: ${triangle.calcArea()}")

    val picture = Picture()
    val total = picture.sumAreas(rectangle, circle, triangle)

    println("\nSum of all areas: $total")
}