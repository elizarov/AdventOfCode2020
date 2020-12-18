package year2020

fun main() = useFile("src/year2020/Day06.in") {
    var cnt = 0
    while (true) {
        val s = HashMap<Char, Int>()
        var pc = 0
        while (true) {
            val a = readLine()?.takeIf { it.isNotEmpty() } ?: break
            for (c in a) {
                s[c] = (s[c] ?: 0) + 1
            }
            pc++
        }
        if (s.isEmpty()) break
        cnt += s.values.count { it == pc }
    }
    println(cnt)
}