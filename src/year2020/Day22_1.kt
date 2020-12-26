package year2020

@OptIn(ExperimentalStdlibApi::class)
fun main() = useFile("src/year2020/Day22.in") {
    fun readDeck(): ArrayDeque<Int> = buildList {
        while (true) {
            readLine()?.takeIf { it.isNotEmpty() }?.toInt()?.let { add(it) } ?: break
        }
    }.toCollection(ArrayDeque())
    check(readLine() == "Player 1:")
    val d1 = readDeck()
    check(readLine() == "Player 2:")
    val d2 = readDeck()
    while (d1.isNotEmpty() && d2.isNotEmpty()) {
        val f1 = d1.removeFirst()
        val f2 = d2.removeFirst()
        if (f1 > f2) {
            d1.add(f1)
            d1.add(f2)
        } else {
            d2.add(f2)
            d2.add(f1)
        }
    }
    val d = d1 + d2
    var mul = 1L
    var sum = 0L
    for (x in d.reversed()) {
        sum += x * (mul++)
    }
    println(sum)
}