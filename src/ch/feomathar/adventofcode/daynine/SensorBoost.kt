package ch.feomathar.adventofcode.daynine

import ch.feomathar.adventofcode.intcode.Computer
import ch.feomathar.adventofcode.util.parseInput

fun boost(fileName: String): Int {
    val lines = parseInput(fileName)
    val code = lines[0].split(",").map { it.toLong() }

    val computer = Computer(code)
    computer.addInput("1")
    computer.runProgram()
    return 0
}
