import java.util.Objects

fun main() {

    //filter instance
    val objs = listOf(Person("Rob", 100000), Person("Anne", 100000), Animal("Dog"), RandomObject("Chair"))
    val newObjs = objs.filterIsInstance<Person>()
    newObjs.forEach{
        println(it.name)
    }
    println()

    //sum of, min of
    val sumBalance = objs.sumOf { if(it is Person) it.balance else 0 }
    val minBalance = objs.minOf { if(it is Person) it.balance else 1000000 }
    println("Sum balance: $sumBalance - Min balance: $minBalance\n")

    //higher order function
    val res = add2numbers(1, 2) { a: Int, b: Int -> a + b }
    println("(higher order function) $res\n")

    //let, apply, also, run, by
    var i: Int? = 1
    var x = i?.let {
        val res = it + 1
        res
    } ?: 96

    println("(let) $x").also { x++ }
    println("(also) $x")

    var person1 = Person("John", 200)
    person1.apply {
        balance *= 2
    }
    println("(apply) ${person1.balance}")

    var person2 = Person("Joe", 100)
    person2.run {
        balance *= 3
        this
    }
    println("(run) ${person2.balance}")
}

fun add2numbers(a: Int, b: Int, fn: (Int, Int) -> Int) = fn(a, b)

class Person(val name: String, var balance: Int)
class Animal(val name: String)
class RandomObject(val name: String)