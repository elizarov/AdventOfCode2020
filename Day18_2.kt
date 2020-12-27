import java.io.*

fun main() {
    check(eval("2 * 3 + (4 * 5)") == 46L)
    val exprs = File("Day18.in").readLines()
    val sum2 = exprs.map { eval(it) }.sum()
    println(sum2)
}

private fun eval(s: String): Long {
    val e = Evaluator2(s)
    return e.eval().also { if (!e.isDone()) e.error() }
}

private class Evaluator2(val s: String) {
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

    fun sum(): Long {
        var res = token()
        while (i < s.length) {
            when (ch()) {
                '+' -> { i++; res += token() }
                ')', '*' -> break
                else -> error()
            }
        }
        return res
    }

    fun eval(): Long {
        var res = sum()
        while (i < s.length) {
            when (ch()) {
                '*' -> { i++; res *= sum() }
                ')' -> break
                else -> error()
            }
        }
        return res
    }

    fun error(): Nothing = error("Invalid:\n$s\n${" ".repeat(i)}^")
    fun isDone(): Boolean = i >= s.length
}