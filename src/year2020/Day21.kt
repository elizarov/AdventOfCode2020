package year2020

import java.io.File
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

fun main() {
    class Data(val c: List<String>, val a: List<String>)
    val dd = File("src/year2020/Day21.in").readLines().map { it ->
        val (cc, aa) = Regex("([^(]+) \\(contains ([^)]+)\\)").matchEntire(it)!!.destructured
        Data(cc.split(" "), aa.split(", "))
    }
    val m = HashMap<String, HashSet<String>>()
    for (d in dd) for (a in d.a) {
        val s = m[a]
        if (s == null) {
            m[a] = d.c.toHashSet()
        } else {
            s.retainAll(d.c)
        }
    }
    for ((a, s) in m) {
        println("$a -> $s")
    }
    val bad = m.values.flatten().toSet()
    println("Bad $bad")
    var cnt1 = 0
    for (d in dd) for (c in d.c) if (c !in bad) cnt1++
    println(cnt1)
    val ai = TreeMap<String,String>()
    while (m.isNotEmpty()) {
        for ((a, s) in m) if (s.size == 1) {
            val i = s.single()
            ai[a] = i
            m.remove(a)
            for (ss in m.values) ss -= i
            break
        }
    }
    println(ai)
    println(ai.values.joinToString(","))
}