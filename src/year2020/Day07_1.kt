package year2020

import java.io.*

fun main() {
    data class Rule(val n: Int, val bag: String)
    val rules = HashMap<String, List<Rule>>()
    File("src/year2020/Day07.in").readLines().map { line ->
        // dull blue bags contain 1 shiny violet bag, 4 plaid magenta bags, 4 dull green bags.
        val (_, name, list) = Regex("([a-z ]+) bags contain (.+)\\.").matchEntire(line)!!.groupValues
        val l = list.split(", ").mapNotNull { s ->
            if (s == "no other bags") null else {
                val (_, ns, bag) = Regex("(\\d+) ([a-z ]+) bags?").matchEntire(s)!!.groupValues
                Rule(ns.toInt(), bag)
            }
        }
        rules.put(name, l)
    }
    val bg = HashMap<String, ArrayList<String>>()
    for ((n, l) in rules) for (m in l) {
        bg.getOrPut(m.bag) { ArrayList() }.add(n)
    }
    val root = "shiny gold"
    val f = HashSet<String>()
    val q = ArrayList<String>()
    fun eqn(s: String) {
        if (f.add(s)) q += s
    }
    eqn(root)
    var i = 0
    while (i < q.size) {
        val n = q[i++]
        bg[n]?.forEach { m -> eqn(m) }
    }
    println(f.size - 1)
}