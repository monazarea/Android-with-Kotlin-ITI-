import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking



fun main() = runBlocking {
    val job1 = launch {
        repeat(10){
            delay(200)
            println("Launch $it")
        }
    }

    val job2 = async {
        repeat(10){
            delay(200)
            println("Async $it")

        }
        return@async "Done" ;
    }
    println(job2.await())
}