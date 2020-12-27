fun main() {
    var a = "716892543"
    repeat(100) {
        val rem = setOf(a[1], a[2], a[3])
        var lbl = a[0]
        do {
            lbl--
            if (lbl == '0') lbl = '9'
        } while(lbl in rem)
        val b = a.substring(0, 1) + a.substring(4)
        val j = b.indexOf(lbl)
        val c = b.substring(0, j + 1) + a.substring(1, 4) + b.substring(j + 1)
        a = c.substring(1) + c[0]
    }
    println("Result: $a")
    val j = a.indexOf('1')
    val b = a.substring(j + 1) + a.substring(0, j)
    println("Answer: $b")
}