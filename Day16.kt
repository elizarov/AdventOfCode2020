import java.io.*

fun main() {
    data class Field(val name: String, val r1: IntRange, val r2: IntRange)
    val fields = ArrayList<Field>()
    val tickets = ArrayList<List<Int>>()
    File("Day16.in").useLines { seq ->
        val it = seq.iterator()
        val lineRegex = Regex("([^:]+): (\\d+)-(\\d+) or (\\d+)-(\\d+)")
        while (true) {
            val line = it.next().takeIf { it.isNotEmpty() } ?: break
            val (_, name, r1a, r1b, r2a, r2b) = lineRegex.matchEntire(line)!!.groupValues
            fields += Field(name, r1a.toInt()..r1b.toInt(), r2a.toInt()..r2b.toInt())
        }
        check(it.next() == "your ticket:")
        tickets += it.next().split(",").map { it.toInt() }
        check(it.next() == "")
        check(it.next() == "nearby tickets:")
        while(it.hasNext()) {
            tickets += it.next().split(",").map { it.toInt() }
        }
    }
    fun Int.isBad() = fields.none { this in it.r1 || this in it.r2 }
    val sum1 = tickets.flatten().filter { it.isBad() }.sum()
    println(sum1)
    val valid = tickets.drop(1).filter { t -> t.none { it.isBad() } }
    println("Valid tickets: ${valid.size}")
    fun Field.findIndices(): List<Int> =
        tickets[0].indices.filter { index ->
            valid.all { t -> t[index] in r1 || t[index] in r2 }
        }
    val indices = fields.associateWith { it.findIndices().toMutableSet() }
    fun dump() {
        println("----------")
        for ((k, v) in indices) {
            println("${k.name} -> $v")
        }
    }
    dump()
    do {
        var changes = false
        for ((k, v) in indices) {
            if (v.size == 1) {
                for ((k2, v2) in indices) {
                    if (k2 != k && v2.remove(v.single())) {
                        changes = true
                    }
                }
            }
        }
    } while(changes)
    dump()
    val prod2 = indices.filterKeys { it.name.startsWith("departure") }
        .values
        .map { tickets[0][it.single()] }
        .fold(1L) { a, b -> a * b }
    println(prod2)
}

operator fun <T> List<T>.component6(): T = this[5]