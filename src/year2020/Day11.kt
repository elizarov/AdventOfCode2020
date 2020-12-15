package year2020

import java.io.*

fun main() {
    var f = File("src/year2020/Day11.in").readLines().map { it.toCharArray() }
    var g = f.map { it.copyOf() }
    val n = f.size
    val m = f[0].size
    do {
        var upd = 0
        for (i in 0 until n) for (j in 0 until m) {
            g[i][j] = f[i][j]
            var occ = 0
            for (di in -1..1) for (dj in -1..1) if (di != 0 || dj != 0) {
                var ii = i + di
                var jj = j + dj
                while (ii in 0 until n && jj in 0 until m && f[ii][jj] == '.') {
                    ii += di
                    jj += dj
                }
                if (ii in 0 until n && jj in 0 until m && f[ii][jj] == '#') occ++
            }
            when(f[i][j]) {
                'L' -> if (occ == 0) {
                    g[i][j] = '#'
                    upd++
                }
                '#' -> if (occ >= 5) {
                    g[i][j] = 'L'
                    upd++
                }
            }
        }
        f = g.also { g = f }
    } while (upd != 0)
    val cnt = f.sumOf { it.count { it == '#' } }
    println(cnt)
}