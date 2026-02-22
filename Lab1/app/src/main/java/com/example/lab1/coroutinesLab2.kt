import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

suspend fun factorial(n:Int):Int{
    var num = n
    var result :Int  = 1
    while (num>0){
        delay(100)
        result*=num;
        num-=1
    }
    return result
}

fun main() = runBlocking {
    val a  = async {
        factorial(5)
    }
    println(a.await())
}
