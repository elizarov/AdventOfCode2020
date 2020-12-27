package year2020

fun main() {
    val a0 = "716892543"
    val n = 1_000_000
    val a = IntArray(n)
    for (i in a0.indices) a[i] = a0[i] - '0'
    for (i in a0.length until n) a[i] = i + 1
    val cups = Array(n + 1) { Cup(it) }
    for (i in 0 until n) {
        cups[a[i]].next = cups[a[(i + 1) % n]]
        cups[a[i]].prev = cups[a[(i + n - 1) % n]]
    }
    var cur = cups[a[0]]
    val rem = IntArray(3)
    repeat(10_000_000) {
        var lst = cur
        for (i in 0..2) {
            lst = lst.next
            rem[i] = lst.i
        }
        var lbl = cur.i
        do {
            lbl--
            if (lbl == 0) lbl = n
        } while(lbl in rem)
        val fst = cur.next
        val nxt = lst.next
        // cur - [fst - ... - lst] - nxt
        cur.next = nxt
        nxt.prev = cur
        val tgt = cups[lbl]
        val tgn = tgt.next
        // tgt - [fst - ... - lst] - tgn
        fst.prev = tgt
        lst.next = tgn
        tgt.next = fst
        tgn.prev = lst
        cur = cur.next
    }
    val c1 = cups[1]
    val n1 = c1.next.i
    val n2 = c1.next.next.i
    println("n1 = $n1")
    println("n2 = $n2")
    val answer = n1.toLong() * n2
    println(answer)
}

class Cup(val i: Int) {
    lateinit var prev: Cup
    lateinit var next: Cup
}
