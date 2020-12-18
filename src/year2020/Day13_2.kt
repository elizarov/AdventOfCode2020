package year2020

@OptIn(ExperimentalStdlibApi::class)
fun main() = useFile("src/year2020/Day13.in") {
    val time = readLine()!!.toLong()
    val buses = readLine()!!.split(",").map { it.toLongOrNull() ?: -1L }
    data class C(val p: Long, val a: Long)
    val cs = buildList {
        for ((i, bus) in buses.withIndex()) {
            if (bus >= 0) add(C(bus, (bus - i % bus) % bus))
        }
    }
    println(cs)
    fun Long.isPrime() = (2L until this).all { k -> this % k != 0L }
    check(cs.all { it.p.isPrime() })
    val p = cs.map { it.p }
    val a = cs.map { it.a }
    val k = p.size
    val x = LongArray(k)
    val r = Array(k) { i -> LongArray(k) { j -> modInv(p[i], p[j]) } }
    for (i in 0 until k) {
        x[i] = a[i]
        for (j in 0 until i) {
            x[i] = r[j][i] * (x[i] - x[j])
            x[i] = x[i] % p[i]
            if (x[i] < 0) x[i] += p[i]
        }
    }
    println("x = ${x.toList()}")
    var res = 0L
    var pp = 1L
    for (i in 0 until k) {
        res += x[i] * pp
        pp *= p[i]
    }
    println(res)
    for (c in cs) {
        println("mod ${c.p} = ${res % c.p}, expected ${c.a}")
    }
}

private fun modInv(a: Long, n: Long): Long {
    var pow = 1L
    repeat((n - 2).toInt()) { pow = (pow * a) % n }
    return pow
}