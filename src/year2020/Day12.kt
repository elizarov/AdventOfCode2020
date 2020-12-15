package year2020

import java.io.*
import kotlin.math.*

fun main() {
    var x = 0
    var y = 0
    var dx = 10
    var dy = 1
    fun rotL(n: Int) = repeat(n) {
        dy = dx.also { dx = -dy }
    }
    fun rotR(n: Int) = rotL(4 - n)
    File("src/year2020/Day12.in").forEachLine { line ->
        val v = line.substring(1).toInt()
        when (line[0]) {
            'N' -> dy += v
            'S' -> dy -= v
            'E' -> dx += v
            'W' -> dx -= v
            'L' -> rotL(v / 90)
            'R' -> rotR(v / 90)
            'F' -> {
                x += v * dx
                y += v * dy
            }
        }
    }
    println(x.absoluteValue + y.absoluteValue)
}