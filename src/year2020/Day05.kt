package year2020

import java.io.*

fun main() {
    val ids = File("src/year2020/Day05.in").readLines().map { line ->
        var id = 0
        for (c in line) {
            id = id shl 1
            when(c) {
                'B', 'R' -> id = id or 1
            }
        }
        id
    }.toSet()
    println(ids.maxOrNull())
    for (i in 8..(1023-8)) {
        if (i !in ids && (i + 1) in ids && (i - 1) in ids) {
            println(i)
        }
    }
}