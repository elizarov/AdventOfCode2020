package year2020

import java.io.*

fun main() {
    val mem1 = HashMap<Long, Long>()
    val mem2 = HashMap<Long, Long>()
    var mask = ""
    File("src/year2020/Day14.in").forEachLine { line ->
        val s = line.split(" ")
        check(s[1] == "=")
        if (s[0] == "mask") {
            mask = s[2].reversed()
        } else {
            val addr = s[0].substringAfter('[').substringBefore(']').toLong()
            val value = s[2].toLong()
            var vm = value
            var af = 0L
            var aa = addr
            var bit = 1L
            for (c in mask) {
                when (c) {
                    '0' -> {
                        vm = vm and bit.inv()
                    }
                    '1' -> {
                        vm = vm or bit
                        aa = aa or bit
                    }
                    'X' -> {
                        af = af or bit
                        aa = aa and bit.inv()
                    }
                }
                bit = bit shl 1
            }
            mem1[addr] = vm
            var cf = af
            while (true) {
                mem2[aa or cf] = value
                if (cf == 0L) break
                cf = (cf - 1) and af
            }
        }
    }
    println("v1 = ${mem1.values.sum()}")
    println("v2 = ${mem2.values.sum()}")

}