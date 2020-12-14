package ch.feomathar.adventofcode.daytwo

import ch.feomathar.adventofcode.intcode.Computer
import ch.feomathar.adventofcode.util.parseInput

fun compute(fileName: String): Int {
    val lines = parseInput(fileName)[0].split(",").map { it.toInt() }.toMutableList()
//    val computer = Computer(lines)
//    computer.setByte(1, 12)
//    computer.setByte(2, 2)
//    computer.runProgram()
//    return computer.getByte(0)

    for (i in 0..99) {
        for (j in 0..99) {
            val computer = Computer(lines)
            computer.setByte(1, i)
            computer.setByte(2, j)
            computer.runProgram()
            if (computer.getByte(0) == 19690720) {
                return 100 * i + j
            }
        }
    }
    return -1
}


