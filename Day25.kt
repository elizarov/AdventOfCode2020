fun main() {
    val sn = 7
    val mod = 20201227
    fun modLog(k: Int): Int {
        var x = 1
        var i = 0
        while (x != k) {
            x = (x * sn) % mod
            i++
        }
        return i
    }
    fun modPow(k: Int, d: Int): Int {
        var x = 1L
        repeat(d) {
            x = (x * k) % mod
        }
        return x.toInt()
    }
    val ck = 11404017
    val dk = 13768789
    val c = modLog(ck)
    val d = modLog(dk)
    println("c = $c")
    println("d = $d")
    val key = modPow(ck, d)
    check(key == modPow(dk, c))
    println("key = $key")
}