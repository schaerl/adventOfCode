package ch.feomathar.adventofcode.dayfour

import ch.feomathar.adventofcode.util.parseInput

private val PATTERN = Regex("((?:\\w+-)*)(\\d+)\\[(\\w{5})]")

fun findReal(fileName: String): Int {
    val lines = parseInput(fileName)
    val rooms = lines.map { Room.parse(it) }
//    return rooms.filter { it.checkReal() }.sumBy { it.id }
    return rooms.filter { it.checkReal() }.filter { it.decipher().contains("northpole", true) }.first().id
}

data class Room(val letters: CharArray, val id: Int, val checksum: CharArray) {
    fun checkReal(): Boolean {
        return calculateCheckSum().contentEquals(checksum)
    }

    fun decipher(): String {
        val rot = id % 26
        val test = ('b' - 'a' + 'a'.toInt()).toChar()
        val rotated = letters.map { if (it == '-' || it == ' ') ' ' else ((((it - 'a') + rot) % 26) + 'a'.toInt()).toChar() }
        return rotated.fold("", { acc, c -> acc + c })
    }

    fun calculateCheckSum(): CharArray {
        return letters
                .filter { it != '-' }
                .groupBy { it }.entries
                .sortedWith(compareBy({ -it.value.size }, { it.key }))
                .map { it.key }
                .take(5)
                .toCharArray()
    }

    companion object {
        fun parse(line: String): Room {
            val m = PATTERN.matchEntire(line)!!
            return Room(m.groupValues[1].toCharArray(), m.groupValues[2].toInt(), m.groupValues[3].toCharArray())
        }
    }
}
