package ch.feomathar.adventofcode.daysix

import ch.feomathar.adventofcode.util.parseInput

fun recover(fileName: String): String {
    var lines = parseInput(fileName)
    val result = List<MutableList<Char>>(lines[0].length) { ArrayList() }
    for (line in lines) {
        for (i in line.indices) {
            result[i].add(line[i])
        }
    }

    return result.map { it.groupBy { it }.toList().sortedBy { it.second.size }[0] }.map { it.first }.joinToString(separator = "")
}
