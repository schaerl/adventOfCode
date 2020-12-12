package ch.feomathar.adventofcode.daytwo

import ch.feomathar.adventofcode.util.parseInput

fun check(fileName: String): Int {
    val lines = parseInput(fileName)
    var numbers = lines.map { it.split(Regex("\\s")) }.map { it.map { it.toInt() }.sorted() }
    return numbers.map { findEvenDivision(it) }.sum()
}

fun findEvenDivision(list: List<Int>): Int {
    for (i in list.size - 1 downTo 0) {
        for (j in 0 until i) {
            if (list[i] % list[j] == 0) {
                return list[i] / list[j]
            }
        }
    }
    throw RuntimeException("No matching values")
}

