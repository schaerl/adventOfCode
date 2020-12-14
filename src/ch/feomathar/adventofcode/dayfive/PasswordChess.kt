package ch.feomathar.adventofcode.dayfive

import ch.feomathar.adventofcode.dayfour.md5

private val INPUT = "ugkcyxxp"

fun findPW(): String {
    var output = HashMap<Int, Char>()
    var nonce = 0
    while (output.size < 8) {
        val toCheck = md5(INPUT + nonce)
        val (index, value) = getCharAtPos(toCheck)
        if (index in 0..7 && !output.containsKey(index)) {
            output[index] = value
        }
        nonce++
    }
    return output.toSortedMap().values.fold("", { acc, c -> acc + c })
}

private fun check(string: String): Boolean {
    return string.startsWith("00000")
}


private fun getCharAtPos(string: String): Pair<Int, Char> {
    if (string.startsWith("00000")) {
        if (string[5].isDigit()) {
            return Pair(string[5].toString().toInt(), string[6])
        }
    }
    return Pair(-1, '0')
}
