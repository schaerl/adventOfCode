package ch.feomathar.adventofcode.dayfive

import ch.feomathar.adventofcode.intcode.Computer
import ch.feomathar.adventofcode.util.parseInput

fun diagnose(fileName: String): String {
    val code = parseInput(fileName)[0]
    val computer = Computer(code.split(",").map { it.toInt() })
//    computer.addInput("1")
    computer.addInput("5")
    computer.runProgram()
    return computer.stdout.joinToString(separator = "\n")
}
