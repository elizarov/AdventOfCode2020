package year2020

import kotlin.math.sqrt

fun main() {
    fun rotate(a: Array<CharArray>): Array<CharArray> {
        val n = a.size
        return Array(n) { i ->
            CharArray(n) { j ->
                a[n - j - 1][i]
            }
        }
    }
    fun flip(a: Array<CharArray>): Array<CharArray> {
        val n = a.size
        return Array(n) { i ->
            CharArray(n) { j ->
                a[j][i]
            }
        }
    }
    fun variants(a: Array<CharArray>) =
        generateSequence(a) { rotate(it) }.take(4) +
        generateSequence(flip(a)) { rotate(it) }.take(4)
    class Tile(val id: Int, a: Array<CharArray>) {
        val r = variants(a).toList()
        var d = 0
    }
    val tiles = ArrayList<Tile>()
    val n = 10
    useFile("src/year2020/Day20.in") {
        while (true) {
            val header = readLine() ?: break
            if (header.isEmpty()) continue
            val id = Regex("Tile (\\d+):").matchEntire(header)!!.groupValues[1].toInt()
            val a = Array(n) { readLine()!!.toCharArray().also { check(it.size == n) } }
            tiles += Tile(id, a)
        }
    }
    println("Parsed ${tiles.size} tiles")
    val m = sqrt(tiles.size.toDouble()).toInt()
    check(tiles.size == m * m)
    val ts = Array(m) { arrayOfNulls<Tile>(m) }
    val u = BooleanArray(tiles.size)
    fun vMatch(t0: Tile, t1: Tile): Boolean {
        val a0 = t0.r[t0.d]
        val a1 = t1.r[t1.d]
        for (k in 0 until n) {
            if (a0[n - 1][k] != a1[0][k]) return false
        }
        return true
    }
    fun hMatch(t0: Tile, t1: Tile): Boolean {
        val a0 = t0.r[t0.d]
        val a1 = t1.r[t1.d]
        for (k in 0 until n) {
            if (a0[k][n - 1] != a1[k][0]) return false
        }
        return true
    }
    fun find(i: Int, j: Int): Boolean {
        if (i == m) return true
        var i2 = i
        var j2 = j + 1
        if (j2 == m) {
            i2++
            j2 = 0
        }
        for (p in tiles.indices) if (!u[p]) {
            val tile = tiles[p]
            ts[i][j] = tile
            u[p] = true
            for (d in 0..7) {
                tile.d = d
                if ((i == 0 || vMatch(ts[i - 1][j]!!, tile)) && (j == 0 || hMatch(ts[i][j - 1]!!, tile))) {
                    if (find(i2, j2)) return true
                }
            }
            u[p] = false
        }
        ts[i][j] = null
        return false
    }
    check(find(0, 0))
    val prod = ts[0][0]!!.id.toLong() * ts[m - 1][0]!!.id * ts[0][m - 1]!!.id * ts[m - 1][m - 1]!!.id
    println("Part 1: found answer with $prod")
    val z = (n - 2) * m
    val r0 = Array(z) { i ->
        val ti = i / (n - 2)
        val oi = i % (n - 2) + 1
        CharArray(z) { j ->
            val tj = j / (n - 2)
            val oj = j % (n - 2) + 1
            val t = ts[ti][tj]!!
            val a = t.r[t.d]
            a[oi][oj]
        }
    }
    val monster = listOf(
        "                  # ",
        "#    ##    ##    ###",
        " #  #  #  #  #  #   "
    )
    val mh = monster.size
    val mw = monster[0].length
    class DM(val i: Int, val j: Int)
    val dms = ArrayList<DM>()
    for (i in 0 until mh) for (j in 0 until mw) if (monster[i][j] == '#') {
        dms += DM(i, j)
    }
    for (r in variants(r0)) {
        var cnt = 0
        for (i0 in 0 until z - mh) for (j0 in 0 until z - mw) {
            if (dms.all { dm -> r[i0 + dm.i][j0 + dm.j] == '#' }) {
                cnt++
                dms.forEach { dm -> r[i0 + dm.i][j0 + dm.j] = 'O' }
            }
        }
        if (cnt > 0) {
            println("Found $cnt monsters")
            val rem = r.sumOf { it.count { it == '#' } }
            println("Part 2: remaining $rem")
            break
        }
    }
}