@OptIn(ExperimentalStdlibApi::class)
fun main() = useFile("Day22.in") {
    fun readDeck(): ArrayDeque<Byte> = buildList {
        while (true) {
            readLine()?.takeIf { it.isNotEmpty() }?.toByte()?.let { add(it) } ?: break
        }
    }.toCollection(ArrayDeque())
    check(readLine() == "Player 1:")
    val d1 = readDeck()
    check(readLine() == "Player 2:")
    val d2 = readDeck()
    class Cfg(val l1: ByteArray, val l2: ByteArray) {
        val hash = run {
            var res = 0
            for (x in l1) { res = res * 239 + x }
            res *= 8191
            for (x in l2) { res = res * 239 + x }
            res
        }
        override fun hashCode(): Int = hash
        override fun equals(other: Any?): Boolean = other is Cfg && other.l1.contentEquals(l1) && other.l2.contentEquals(l2)
        override fun toString(): String = "${l1.contentToString()} -- ${l2.contentToString()}"
    }
    fun cfg(d1: ArrayDeque<Byte>, d2: ArrayDeque<Byte>) = Cfg(d1.toByteArray(), d2.toByteArray())
    val dp = Array(d1.size + d2.size + 1) { HashMap<Cfg, Int>() }
    var total = 0L
    fun play(d1: ArrayDeque<Byte>, d2: ArrayDeque<Byte>, level: Int): Int {
        val d = dp[d1.size + d2.size]
        val cs = HashSet<Cfg>()
        var res = 0
        while (d1.isNotEmpty() && d2.isNotEmpty()) {
            val cfg = cfg(d1, d2)
            val oldRes = d[cfg]
            if (oldRes != null) {
                res = oldRes
                break
            }
            if (!cs.add(cfg)) {
                res = 1
                break
            }
            val f1 = d1.removeFirst()
            val f2 = d2.removeFirst()
            val win = if (f1 <= d1.size && f2 <= d2.size) {
                val c1 = d1.subList(0, f1.toInt()).toCollection(ArrayDeque())
                val c2 = d2.subList(0, f2.toInt()).toCollection(ArrayDeque())
                val hr = heuristic(c1, c2)
                if (hr != 0) hr else {
                    play(c1, c2, level + 1)
                }
            } else {
                if (f1 > f2) 1 else 2
            }
            when(win) {
                1 -> {
                    d1.add(f1)
                    d1.add(f2)
                }
                2 -> {
                    d2.add(f2)
                    d2.add(f1)
                }
                else -> error("cannot be")
            }
            if (level == 0) {
                println("[$level] ++ STEP")
            }
        }
        if (res == 0) res = if (d1.isNotEmpty()) 1 else 2
        for (c in cs) d[c] = res
        val prevSize = total
        total += cs.size
        if (total shr 18 != prevSize shr 18) {
            println("[$level] Played $total rounds; last ${cs.size}; deck ${d1.size + d2.size} cards, win $res")
            if (d.size > 1_000_000) {
                d.clear()
                println("!!!!! CLEARED CURRENT LEVEL CACHE")
            }
        }
        return res
    }
    val win = play(d1, d2, 0)
    println("Played ${dp.size} rounds")
    println("Winner is $win")
    println("d1 = $d1")
    println("d2 = $d2")
    val d = d1 + d2
    var mul = 1L
    var sum = 0L
    for (x in d.reversed()) {
        sum += x * (mul++)
    }
    println(sum)
}

private fun heuristic(d1: List<Byte>, d2: List<Byte>): Int {
    val m1 = d1.maxOrNull()!!
    val m2 = d2.maxOrNull()!!
    if (m1 > m2 && m1 >= d1.size + d2.size) return 1
    return 0
}