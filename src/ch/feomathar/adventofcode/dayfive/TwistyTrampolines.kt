package ch.feomathar.adventofcode.dayfive

import ch.feomathar.adventofcode.util.parseInput

fun findExit(fileName: String): Int {
    val instructions = parseInput(fileName).map { it.toInt() }.toMutableList()
    var pc = 0
    var steps = 0
    while (pc in instructions.indices) {
        val jumps = instructions[pc]
        if (jumps >= 3) {
            instructions[pc]--
        } else {
            instructions[pc]++
        }
        pc += jumps
        steps++
    }
    return steps
}
