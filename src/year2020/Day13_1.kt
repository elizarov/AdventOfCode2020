package year2020

fun main() = useFile("src/year2020/Day13.in") {
    val time = readLine()!!.toLong()
    val buses = readLine()!!.split(",").filter { it != "x" }.map { it.toLong() }
    val dt = buses.associateWith { bus -> bus - time % bus }
    val best = dt.entries.minByOrNull { it.value }!!
    println(best)
    println(best.key * best.value)
}