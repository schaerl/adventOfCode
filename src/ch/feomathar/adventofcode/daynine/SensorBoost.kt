package ch.feomathar.adventofcode.daynine

import ch.feomathar.adventofcode.intcode.Computer
import ch.feomathar.adventofcode.util.parseInput

fun boost(fileName: String): String {
    val lines = parseInput(fileName)
    val code = lines[0].split(",").map { it.toLong() }

    val computer = Computer(code)
//    computer.addInput("1")
    computer.addInput("2")
    computer.runProgram()
    return computer.stdout.removeLast()
}
