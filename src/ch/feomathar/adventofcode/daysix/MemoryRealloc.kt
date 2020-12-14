package ch.feomathar.adventofcode.daysix

import ch.feomathar.adventofcode.util.parseInput

fun countCycle(fileName: String): Int {
    val numberLine = parseInput(fileName)
    val numbers = numberLine[0].split("\t").map { it.toInt() }

    val seen = ArrayList<List<Int>>()

    var current = ArrayList(numbers)
    var iterations = 0
    do {
        seen.add(current)
        current = ArrayList(current)
        var max = 0
        var maxIndex = 0
        for (i in current.indices) {
            if (current[i] > max) {
                maxIndex = i
                max = current[i]
            }
        }

        current[maxIndex] = 0
        do {
            maxIndex = (maxIndex + 1) % numbers.size
            current[maxIndex]++
            max--
        } while (max > 0)
        iterations++
    } while (!seen.contains(current))

//    return iterations
    return iterations - seen.indexOf(current)
}
