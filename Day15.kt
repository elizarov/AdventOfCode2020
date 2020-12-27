fun main() {
    val nums = "0,3,1,6,7,5".split(",").map { it.toInt() }.toMutableList()
    val idx = HashMap<Int, Int>()
    for ((i, last) in nums.dropLast(1).withIndex()) {
        idx[last] = i
    }
    while (nums.size < 30000000) {
        val last = nums.last()
        val j = nums.lastIndex
        val i = idx[last] ?: -1
        nums.add(if (i < 0) 0 else j - i)
        idx[last] = j
    }
    println(nums.last())
}