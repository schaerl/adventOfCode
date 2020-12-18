package ch.feomathar.adventofcode.dayeight

import ch.feomathar.adventofcode.util.parseInput

fun escape(fileName: String): Int {
    val lines = parseInput(fileName)
    return lines.map { escapeCount(it)/* it.length - getEscaped(it).length */ }.sum()
}

fun escapeCount(string: String): Int {
    var sum = 2
    sum += string.count { it == '\"' }
    sum += string.count { it == '\\' }
    return sum
}


fun getEscaped(string: String): String {
    var result = ""
    var processing = string.removePrefix("\"")
    processing = processing.removeSuffix("\"")
    var escaping = false
    var hex = false
    var hexVal = ""
    for (char in processing) {
        if (!escaping) {
            if (char == '\\') {
                escaping = true
                continue
            } else {
                result += char
            }
        } else {
            if (char == 'x') {
                hex = true
                continue
            } else if (hex) {
                hexVal += char
                if (hexVal.length == 2) {
                    escaping = false
                    hex = false
                    result += hexVal.toInt(16).toChar()
                    hexVal = ""
                }
                continue
            } else {
                result += char
                escaping = false
            }
        }
    }
    return result
}

