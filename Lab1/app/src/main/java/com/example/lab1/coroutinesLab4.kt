import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

suspend fun sum(items: Array<Int>):Int{
    var result :Int = 0
    for(item in items){
        result += item
    }
    delay(100)
    return result
}
fun main()= runBlocking{
    var result = async {
        sum(arrayOf(1,2,3,4))
    }
    println(result.await())
}