class UgaBuga<in T> {
    private val list = mutableListOf<T>()

    fun push(value: T) {
        list.add(value as T)
    }

    fun pop(): T? {
        val newValue = list.last()
        list.removeAt(list.size - 1)
        return newValue
    }

    fun size(): Int {
        return list.size
    }
}

open class A

class B : A()

fun main(args: Array<String>) {
    val holder = UgaBuga<UgaBuga<A>>()
    val holderB = UgaBuga<B>()
    val holderA = UgaBuga<A>()
    holder.push(holderA)
    holder.push(holderB)
    println(holder.size())
}

