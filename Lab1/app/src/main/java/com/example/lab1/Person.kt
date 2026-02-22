package com.example.lab1

enum class Gender {
    MALE,
    FEMALE
}

data class Person(
    var name: String,
    var id: Int,
    var gender: Gender,
    var address: String
)

fun main() {
    val person: Person? = Person("Mona", 101, Gender.FEMALE,"Egypt")

    person?.let {
        println("let → Name: ${it.name}, ID: ${it.id}, Gender: ${it.gender}, Address: ${it.address}")
    }

    val person2 = Person("", 0, Gender.MALE, "").apply {
        name = "Ahmed"
        id = 102
        gender = Gender.MALE
        address = "Egypt"
    }
    println("apply → Name: ${person2.name}, ID: ${person2.id}, Gender: ${person2.gender}, Address: ${person2.address}")

    val person3 = Person("Ali", 103, Gender.MALE, "Egypt").also {
        println("also → Created person with name ${it.name}")
    }

    val person4 = Person("Sara", 104, Gender.FEMALE,"Egypt")
    with(person4) {
        println("with → Name: $name")
        println("with → ID: $id")
        println("with → Gender: $gender")
        println("with → Address: $address")
    }

    val person5 = Person("Laila", 105, Gender.FEMALE, "Egypt")
    val description = person5.run {
        name = "Laila A."
        id += 100
        "run → Name: $name, ID: $id, Gender: $gender, Address: $address"
    }
    println(description)
}
