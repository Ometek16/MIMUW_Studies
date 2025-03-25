enum class Color(val rgb: Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF)
}

sealed class Color2(val rgb: Int) {
    object RED : Color2(0xFF0000)
    object GREEN : Color2(0x00FF00)
    object BLUE : Color2(0x0000FF)
}

object CoolCounter {
    var cnt = 0;

    fun increment() {
        cnt += 1;
    }
}


fun main() {
    println(Color2.BLUE.rgb)

    var c1 = CoolCounter
    var c2 = CoolCounter

    c1.increment()
    c1.increment()
    c2.increment()

    println(c2.cnt)


}