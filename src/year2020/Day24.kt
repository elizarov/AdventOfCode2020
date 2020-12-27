package year2020

import java.io.*

fun main() {
    data class TC(val x: Int, val y: Int)
    val bt = HashSet<TC>()
    val flips = File("src/year2020/Day24.in").readLines()
    for (f in flips) {
        var x = 0
        var y = 0
        var i = 0
        while (i < f.length) {
            when (f[i++]) {
                'e' -> x++
                'w' -> x--
                'n' -> when(f[i++]) {
                    'e' -> y++
                    'w' -> { y++; x-- }
                    else -> error("!")
                }
                's' -> when(f[i++]) {
                    'w' -> y--
                    'e' -> { y--; x++ }
                    else -> error("!")
                }
                else -> error("!")
            }
        }
        val t = TC(x, y)
        if (t in bt) bt -= t else bt += t
    }
    println("Part 1: ${bt.size}")
    var a = bt
    var b = HashSet<TC>()
    repeat(100) {
        val nc = a.flatMap { t ->
            listOf(
                TC(t.x + 1, t.y), TC(t.x - 1, t.y),
                TC(t.x, t.y + 1), TC(t.x - 1, t.y + 1),
                TC(t.x, t.y - 1), TC(t.x + 1, t.y - 1),
            )
        }.groupingBy { it }.eachCount()
        a.filterTo(b) { nc[it] in 1..2 }
        nc.filterValues { it == 2 }.keys.filterTo(b) { it !in a }
        a = b.also { b = a }
        b.clear()
    }
    println("Part 2: ${a.size}")
}