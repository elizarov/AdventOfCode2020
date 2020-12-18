package year2020

import java.io.*

fun main() {
    val a = File("src/year2020/Day01.in").readLines().map { it.toInt() }
    for (i in a.indices) for (j in 0 until i) if (a[i] + a[j] == 2020) {
        println(a[i] * a[j])
    }
    for (i in a.indices) for (j in 0 until i) for (k in 0 until j) if (a[i] + a[j] + a[k] == 2020) {
        println(a[i] * a[j] * a[k])
    }
}