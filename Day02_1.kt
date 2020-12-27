import java.io.*

fun main() {
    val regex = Regex("(\\d+)-(\\d+) (.): (.+)")
    var good = 0
    File("Day02.in").readLines().map { line ->
        val (_, a, b, c, d) = regex.matchEntire(line)!!.groupValues
        val cc = c[0]
        if (d.count { it == cc } in a.toInt()..b.toInt()) good++
    }
    println(good)
}