package year2020

import java.io.*

fun main() {
    check(eval("2 * 3 + (4 * 5)") == 26L)
    val exprs = File("src/year2020/Day18.in").readLines()
    val sum1 = exprs.map { eval(it) }.sum()
    println(sum1)
}

private fun eval(s: String): Long {
    val e = Evaluator(s)
    return e.eval().also { if (!e.isDone()) e.error() }
}

private class Evaluator(val s: String) {
    private var i = 0

    fun ch(): Char {
        while (i < s.length && s[i] == ' ') i++
        return s[i]
    }

    fun token(): Long =
        when (ch()) {
            '(' -> {
                i++
                eval().also {
                    check(ch() == ')')
                    i++
                }
            }
            else -> {
                val c = ch()
                if (c !in '0'..'9') error()
                (c - '0').toLong().also { i++ }
            }
        }

    fun eval(): Long {
        var res = token()
        while (i < s.length) {
            when (ch()) {
                '+' -> { i++; res += token() }
                '*' -> { i++; res *= token() }
                ')' -> break
                else -> error()
            }
        }
        return res
    }

    fun error(): Nothing = error("Invalid:\n$s\n${" ".repeat(i)}^")
    fun isDone(): Boolean = i >= s.length
}