//enum class Building {
//    VILLA,
//    APARTMENT
//}
//
//
//data class Person(
//    val name: String,
//    val address: Address?
//) {
//    data class Address(
//        val streetName: String,
//        val city: String,
//        val building: Building
//    )
//}
//
//
//fun main() {
//    val mona = Person("Mona", Person.Address("El-Nasr St", "Cairo", Building.VILLA))
//    val movedMona = mona.copy(address = mona.address?.copy(city = "Alexandria"))
//
//
//    println("Name: ${mona.name}")
//
//    val myAddress = mona.address
//    println(myAddress)
//    println("Street: ${myAddress?.streetName}")
//    println("City: ${myAddress?.city}")
//    println("Building: ${myAddress?.building}")
//}
//
