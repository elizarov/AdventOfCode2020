import java.io.*

fun main() {
    val a = File("Day03.in").readLines()
    val ds = listOf(1 to 1, 3 to 1, 5 to 1, 7 to 1, 1 to 2)
    var prod = 1L
    for (d in ds) {
        var i = 0
        var j = 0
        var cnt = 0
        while (i < a.size) {
            if (a[i][j] == '#') cnt++
            i += d.second
            j = (j + d.first) % a[0].length
        }
        println("$d -> $cnt")
        prod *= cnt
    }
    println(prod)
}