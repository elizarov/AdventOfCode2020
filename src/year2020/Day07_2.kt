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
    val dp = HashMap<String, Long>()
    fun compute(name: String): Long {
       dp[name]?.let { return it }
       var cnt = 1L
       for (r in rules[name]!!) cnt += r.n * compute(r.bag)
       dp[name] = cnt
       return cnt
    }
    val root = "shiny gold"
    println(compute(root) - 1)
}