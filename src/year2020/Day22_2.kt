package year2020

@OptIn(ExperimentalStdlibApi::class)
fun main() = useFile("src/year2020/Day22.in") {
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
            for (x in l2) { res = res * 239 + x }
            res
        }
        override fun hashCode(): Int = hash
        override fun equals(other: Any?): Boolean = other is Cfg && other.l1.contentEquals(l1) && other.l2.contentEquals(l2)
        override fun toString(): String = "${l1.contentToString()} -- ${l2.contentToString()}"
    }
    fun cfg(d1: ArrayDeque<Byte>, d2: ArrayDeque<Byte>) = Cfg(d1.toByteArray(), d2.toByteArray())
    val dp = HashMap<Cfg, Int>()
    var total = 0L
    fun play(d1: ArrayDeque<Byte>, d2: ArrayDeque<Byte>): Int {
        val cs = HashSet<Cfg>()
        var res = 0
        while (d1.isNotEmpty() && d2.isNotEmpty()) {
            val cfg = cfg(d1, d2)
            val oldRes = dp[cfg]
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
                val c1 = d1.toCollection(ArrayDeque())
                val c2 = d2.toCollection(ArrayDeque())
                play(c1, c2)
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
        }
        if (res == 0) res = if (d1.isNotEmpty()) 1 else 2
        val prevSize = dp.size
        for (c in cs) dp[c] = res
        if (dp.size / 10000 != prevSize / 10000) {
            println("Played ${dp.size} games, last one with ${cs.size} rounds")
            if (dp.size > 50_000_000) {
                total += dp.size
                dp.clear()
                println("!!!!! CLEAR: TOTAL ROUNDS PLAYED: $total")
            }
        }
        return res
    }
    val win = play(d1, d2)
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
