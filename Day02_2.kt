import java.io.*

fun main() {
    val regex = Regex("(\\d+)-(\\d+) (.): (.+)")
    var good = 0
    File("Day02.in").readLines().map { line ->
        val (_, a, b, c, d) = regex.matchEntire(line)!!.groupValues
        val cc = c[0]
        var g =
            (if (d.getOrNull(a.toInt() - 1) == cc) 1 else 0) +
            (if (d.getOrNull(b.toInt() - 1) == cc) 1 else 0)
        if (g == 1) good++
    }
    println(good)
}