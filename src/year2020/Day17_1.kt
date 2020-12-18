package year2020

import java.io.*

@OptIn(ExperimentalStdlibApi::class)
fun main() {
    data class Cube(val i: Int, val j: Int, val k: Int)

    val cfg = File("src/year2020/Day17.in").readLines()
    var cubes = buildSet {
        for (i in cfg.indices) for (j in cfg[i].indices) if (cfg[i][j] == '#') add(Cube(i, j, 0))
    }
    repeat(6) {
        val n = cubes.flatMap { (i, j, k) ->
            buildList {
                for (di in -1..1) for (dj in -1..1) for (dk in -1..1) if (di != 0 || dj != 0 || dk != 0) {
                    add(Cube(i + di, j + dj, k + dk))
                }
            }
        }
        val nc = n.groupingBy { it }.eachCount()
        cubes = cubes.filter { (nc[it] ?: 0) in 2..3 }.toSet() + nc.filter { it.value == 3 }.keys
    }
    println(cubes.size)
}