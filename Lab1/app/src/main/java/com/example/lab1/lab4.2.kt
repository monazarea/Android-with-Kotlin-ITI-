enum class Type{
    BOOK,
    MAGAZINE,
    JOURNAL
}

open class Person(val name:String,val id:Int)
class Librarian(name:String , id:Int,val password :String):Person(name,id)
class User(name:String, id:Int,val Job:String):Person(name,id)

open class LibraryItem(var title: String,var ISBN:String, var publication:String, var numberOfBages:Int,var quantity:Int){
    fun isAvailable(): Boolean{
        return quantity>0;
    }
}
class Book(title:String,ISBN: String,publication: String,numberOfBages: Int,quantity:Int):LibraryItem(title,ISBN,publication,numberOfBages,quantity)
class Magazine(title:String,ISBN: String,publication: String,numberOfBages: Int,quantity:Int):LibraryItem(title,ISBN,publication,numberOfBages,quantity)
class Journal(title:String,ISBN: String,publication: String,numberOfBages: Int,quantity:Int):LibraryItem(title,ISBN,publication,numberOfBages,quantity)

class LibraryDataBase(val currnetLibrarian:Librarian,val availableItems: MutableList<LibraryItem>,borrowedBooks:MutableList<LibraryItem>,val users: MutableList<User>){

    fun addItem(libraryItem:LibraryItem){
        availableItems.add(libraryItem)
        println("${libraryItem.title} added to library")
    }
    fun searchForItem(title: String):LibraryItem?{
        return availableItems.find { it.title.equals(title,ignoreCase = true) }
    }

    fun lendItem(user:User,libraryItem: LibraryItem){
        val item:LibraryItem? = searchForItem(libraryItem.title);

        item?.takeIf { it.isAvailable() }?.let {
            it.quantity--;
            println("${user.name} borrowed '${it.title}'")
        }?: println("Book '${libraryItem.title}' is not available")
    }

    fun viewAvailableItems(type: Type) {
        val items = availableItems.filter { it.isAvailable() }

        val filtered = when(type) {
            Type.BOOK -> items.filter { it is Book }
            Type.MAGAZINE -> items.filter { it is Magazine }
            Type.JOURNAL -> items.filter { it is Journal }
        }

        if(filtered.isEmpty()) {
            println("No available ${type.name.lowercase()}s")
        } else {
            println("Available ${type.name.lowercase()}s:")
            filtered.forEach { println(it.title) }
        }
    }

    fun reciveItemFromBorrower(user:User,libraryItem: LibraryItem){
        val item = searchForItem(libraryItem.title)

        item?.let {
            it.quantity+=1
            println("${user.name} returned '${it.title}'. Current available copies: ${it.quantity}")
        } ?: println("Item '${libraryItem.title}' not found in library")

    }

}
fun main(){
    val librarian = Librarian("Ali", 1, "1234")
    val user1 = User("Mona", 101, "Student")
    val user2 = User("esraa", 102, "Teacher")

    val library = LibraryDataBase(
        currnetLibrarian = librarian,
        availableItems = mutableListOf(),
        borrowedBooks = mutableListOf(),
        users = mutableListOf(user1, user2)
    )

    val book1 = Book("Kotlin Basics", "ISBN001", "2026", 200, 2)
    val book2 = Book("Java Fundamentals", "ISBN002", "2025", 300, 1)
    val magazine1 = Magazine("Tech Today", "MAG001", "2026", 50, 3)
    val journal1 = Journal("AI Research", "JRN001", "2024", 100, 1)


    library.addItem(book1)
    library.addItem(book2)
    library.addItem(magazine1)
    library.addItem(journal1)

    println("____________________________________________")

    library.viewAvailableItems(Type.BOOK)
    library.viewAvailableItems(Type.MAGAZINE)
    library.viewAvailableItems(Type.JOURNAL)

    println("____________________________________________")

    library.lendItem(user1, book1)
    library.lendItem(user2, book2)
    library.lendItem(user1, journal1)

    library.viewAvailableItems(Type.BOOK)
    library.viewAvailableItems(Type.MAGAZINE)
    library.viewAvailableItems(Type.JOURNAL)

    println("____________________________________________")

    library.reciveItemFromBorrower(user1, book1)
    library.reciveItemFromBorrower(user2, book2)

    library.viewAvailableItems(Type.BOOK)
    library.viewAvailableItems(Type.MAGAZINE)
    library.viewAvailableItems(Type.JOURNAL)
}
