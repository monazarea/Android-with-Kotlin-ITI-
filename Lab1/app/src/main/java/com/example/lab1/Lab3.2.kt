
interface NubmerSeries {
    var start : Int
    var current : Int
    fun reset()
    fun getNext(): Int
}
class ByTwo : NubmerSeries{
    override var current: Int = 0
    override var start: Int = 0
        set(value){
            current = value
            field = value
        }

    override fun reset() {
        current = start
    }
    override  fun getNext():Int{
        current+=2
        return  current
    }
}

class ByThree : NubmerSeries{
    override  var current : Int = 0
    override  var start : Int = 0
        set(value){
            field = value
            current = value
        }
    override  fun reset(){
        current = start
    }

    override fun getNext(): Int {
        current+=3
        return current
    }
}


fun main(){
    val byTwos = ByTwo()
    val byThree = ByThree()

    var ref : NubmerSeries
    repeat(5){
        ref = byTwos
        println("By two:${ref.getNext()}")
        ref = byThree
        println("By three: ${ref.getNext()}")
    }
}