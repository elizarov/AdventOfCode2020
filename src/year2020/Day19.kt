package year2020

sealed class Rule
class Letter(val c: Char) : Rule()
class Seq(val r1: Int, val r2: Int? = null) : Rule()
class Choice(val p1: Seq, val p2: Seq) : Rule()
// part2
class Loop(val r: Int) : Rule()
class Brace(val r1: Int, val r2: Int) : Rule()

private fun String.toSeq() = trim().let {
    when {
        it.contains(' ') -> Seq(it.substringBefore(" ").toInt(), it.substringAfter(" ").toInt())
        else -> Seq(it.toInt())
    }
}

fun main() = useFile("src/year2020/Day19.in") {
    val rules = HashMap<Int, Rule>()
    while (true) {
        val s = readLine()?.takeIf { it.isNotEmpty() } ?: break
        val r = s.substringAfter(": ")
        val rule = when {
            r.startsWith("\"") -> Letter(r[1])
            r.contains('|') -> Choice(r.substringBefore("|").toSeq(), r.substringAfter("|").toSeq())
            else -> r.toSeq()
        }
        rules[s.substringBefore(":").toInt()] = rule
    }
    val strings = ArrayList<String>()
    while (true) {
        val s = readLine() ?: break
        strings += s
    }
    part1(strings, rules)
    part2(strings, rules)
}

private fun part1(
    strings: ArrayList<String>,
    rules: HashMap<Int, Rule>
) {
    println("--- Part 1")
    var cnt = 0
    for (s in strings) {
        val n = s.length
        val dp = HashMap<Int, Boolean>()
        fun matches(i: Int, j: Int, r: Int): Boolean {
            if (i == j) return false
            val idx = (r * n + i) * n + j
            dp[idx]?.let { return it }
            fun checkSeq(seq: Seq): Boolean {
                val r1 = seq.r1
                val r2 = seq.r2 ?: return matches(i, j, r1)
                for (k in i + 1 until j) {
                    if (matches(i, k, r1) && matches(k, j, r2)) return true
                }
                return false
            }

            val result = when (val rule = rules[r]!!) {
                is Letter -> j == i + 1 && s[i] == rule.c
                is Seq -> checkSeq(rule)
                is Choice -> checkSeq(rule.p1) || checkSeq(rule.p2)
                else -> error("cannot happen")
            }
            dp[idx] = result
            return result
        }

        val m = matches(0, n, 0)
        println("$s -> $m")
        if (m) cnt++
    }
    println(cnt)
}

private fun part2(
    strings: ArrayList<String>,
    rules: HashMap<Int, Rule>
) {
    println("--- Part 2")
    rules[8] = Loop(42)
    rules[11] = Brace(42, 31)
    var cnt = 0
    for (s in strings) {
        val n = s.length
        val dp = HashMap<Int, Boolean>()
        fun matches(i: Int, j: Int, r: Int): Boolean {
            if (i == j) return false
            val idx = (r * n + i) * n + j
            dp[idx]?.let { return it }
            fun checkSeq(seq: Seq): Boolean {
                val r1 = seq.r1
                val r2 = seq.r2 ?: return matches(i, j, r1)
                for (k in i + 1 until j) {
                    if (matches(i, k, r1) && matches(k, j, r2)) return true
                }
                return false
            }
            fun checkLoop(loop: Loop): Boolean {
                for (k in i + 1..j) {
                    if (matches(i, k, loop.r) && (k == j || matches(k, j, r))) return true
                }
                return false
            }
            fun checkBrace(br: Brace): Boolean {
                for (k in i + 1 until j) {
                    for (t in k until j) {
                        if (matches(i, k, br.r1) && matches(t, j, br.r2) && (t == k || matches(k, t, r))) return true
                    }
                }
                return false
            }

            val result = when (val rule = rules[r]!!) {
                is Letter -> j == i + 1 && s[i] == rule.c
                is Seq -> checkSeq(rule)
                is Choice -> checkSeq(rule.p1) || checkSeq(rule.p2)
                is Loop -> checkLoop(rule)
                is Brace -> checkBrace(rule)
            }
            dp[idx] = result
            return result
        }

        val m = matches(0, n, 0)
        println("$s -> $m")
        if (m) cnt++
    }
    println(cnt)
}

