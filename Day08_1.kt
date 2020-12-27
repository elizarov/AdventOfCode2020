import java.io.*

fun main() {
    data class Inst(val op: String, val n: Int)
    val code = File("Day08.in").readLines().map { line ->
        Inst(line.substringBefore(' '), line.substringAfter(' ').toInt())
    }
    var pc = 0
    val f = BooleanArray(code.size)
    var a = 0L
    while (!f[pc]) {
        f[pc] = true
        val inst = code[pc]
        when (inst.op) {
            "acc" -> { a += inst.n; pc++ }
            "jmp" -> pc += inst.n
            "nop" -> pc++
            else -> error(inst.toString())
        }
    }
    println(a)
}