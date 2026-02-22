import com.example.lab1.calculate
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.cancellation.CancellationException

fun main() = runBlocking {

    var job1 = launch {
        try {
            repeat(100) {
                println("Job1 $it")
                delay(100)
            }

        }
        catch (e: CancellationException){
            println("job 1 cancelled")
        }
        finally {
            println("job 1 cleaned resources")
        }
    }
    job1.join()

    var job2 = launch {
        try{
            repeat(100){
                println("Job2 $it")
                delay(100)
            }

        }
        catch (e:CancellationException ){
            println("job2 cancelled")
        }
        finally {
            println("job 2 cleaned resources")

        }
    }
    delay(1000)
    println("job2: waiting using join()")
    job2.cancelAndJoin()
//    println("Job2 finished waiting")
//    job1.cancelAndJoin()
//    println("Job1:Canceled Done")



}