import java.io.*

fun main() {
    data class Inst(var op: String, val n: Int)
    val code = File("Day08.in").readLines().map { line ->
        Inst(line.substringBefore(' '), line.substringAfter(' ').toInt())
    }
    fun execute(): Long? {
        var pc = 0
        val f = BooleanArray(code.size)
        var a = 0L
        while (pc in code.indices && !f[pc]) {
            f[pc] = true
            val inst = code[pc]
            when (inst.op) {
                "acc" -> { a += inst.n; pc++ }
                "jmp" -> pc += inst.n
                "nop" -> pc++
                else -> error(inst.toString())
            }
        }
        if (pc == code.size) return a
        return null
    }
    for (inst in code) {
        when (inst.op) {
            "nop" -> {
                inst.op = "jmp"
                execute()?.let { println(it) }
                inst.op = "nop"
            }
            "jmp" -> {
                inst.op = "nop"
                execute()?.let { println(it) }
                inst.op = "jmp"
            }
        }
    }
}