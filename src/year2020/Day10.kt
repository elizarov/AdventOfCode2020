package year2020

import java.io.*

fun main() {
    val inp = File("src/year2020/Day10.in")
        .readLines()
        .map { it.toInt() }
        .sorted()
    val all = (listOf(0) + inp + (inp.last() + 3))
    val diffs = all.zipWithNext().map { (a, b) -> b - a }
    check(diffs.all { it in 1..3 })
    val d1 = diffs.count { it == 1 }
    val d3 = diffs.count { it == 3 }
    println("Part1 = ${d1 * d3}")
    val n = all.size
    val dp = LongArray(n)
    dp[0] = 1
    for (i in 1 until n) {
        for (j in (i - 3).coerceAtLeast(0) until i) {
            if (all[i] - all[j] <= 3) {
                dp[i] += dp[j]
            }
        }
    }
    println("Part2 = ${dp.last()}")
}


