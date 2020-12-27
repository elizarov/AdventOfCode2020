import java.io.*

fun main() {
    val nums = File("Day09.in").readLines().map { it.toLong() }
    val k = 25
    fun List<Long>.okWindow(): Boolean {
        val last = last()
        for (i in 1 until k) for (j in 0 until i) {
            if (this[i] + this[j] == last) return true
        }
        return false
    }
    val t = nums.asSequence().windowed(k + 1).first { !it.okWindow() }.last()
    println("weakness = $t")
    var i = 0
    var j = 0
    var sum = 0L
    while (sum != t) {
        while (sum < t) sum += nums[j++]
        while (sum > t) sum -= nums[i++]
    }
    println("i = $i")
    println("j = $j")
    val list = nums.subList(i, j)
    println("sublist = $list")
    println(list.minOrNull()!! + list.maxOrNull()!!)
}