fun main() = useFile("Day06.in") {
    var cnt = 0
    while (true) {
        val s = HashSet<Char>()
        while (true) {
            val a = readLine()?.takeIf { it.isNotEmpty() } ?: break
            s.addAll(a.toSet())
        }
        if (s.isEmpty()) break
        cnt += s.size
    }
    println(cnt)
}