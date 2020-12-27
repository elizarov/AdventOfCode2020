@OptIn(ExperimentalStdlibApi::class)
fun main() = useFile("Day04.in") {
    fun readPassport(): Map<String,String> = buildMap {
        while (true) {
            val line = readLine()?.takeIf { it.isNotBlank() } ?: break
            line.split(" ").forEach { p ->
                put(p.substringBefore(":"), p.substringAfter(":"))
            }
        }
    }
    var valid1 = 0
    var valid2 = 0
    val req= listOf("byr", "iyr", "eyr", "hgt" , "hcl", "ecl", "pid")
    fun Map.Entry<String, String>.isValid(): Boolean = when(key) {
        "byr" -> value.length == 4 && value.toIntOrNull()?.let { it in 1920..2002 } ?: false
        "iyr" -> value.length == 4 && value.toIntOrNull()?.let { it in 2010..2020 } ?: false
        "eyr" -> value.length == 4 && value.toIntOrNull()?.let { it in 2020..2030 } ?: false
        "hgt" -> when {
            value.endsWith("cm") -> value.dropLast(2).toIntOrNull()?.let { it in 150..193 } ?: false
            value.endsWith("in") -> value.dropLast(2).toIntOrNull()?.let { it in 59..76 } ?: false
            else -> false
        }
        "hcl" -> value.matches(Regex("#[0-9a-f]{6}"))
        "ecl" -> value in setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
        "pid" -> value.matches(Regex("[0-9]{9}"))
        else -> true
    }
    while (true) {
        val p = readPassport()
        if (p.isEmpty()) break
        if (p.keys.containsAll(req)) {
            valid1++
            if (p.entries.all { it.isValid() }) valid2++
        }
    }
    println(valid1)
    println(valid2)

}