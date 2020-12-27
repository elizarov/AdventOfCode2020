import java.io.*

fun useFile(name: String, block: BufferedReader.() -> Unit) {
    File(name).bufferedReader().use {
        it.block()
    }
}