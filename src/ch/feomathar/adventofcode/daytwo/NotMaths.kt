package ch.feomathar.adventofcode.daytwo

import ch.feomathar.adventofcode.util.parseInput

fun execute(fileName: String): Int {
    var lines = parseInput(fileName)
    var intArrays = lines.map { it.split("x") }.map { strArr -> strArr.map { it.toInt() } }
    return intArrays
            .map { Pair(intArrayOf(it[0] + it[1], it[0] + it[2], it[1] + it[2]).sorted(), it[0] * it[1] * it[2]) }
            .map { 2 * it.first[0] + it.second }
            .sum()
}

