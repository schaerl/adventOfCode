package ch.feomathar.adventofcode.daytwo

import ch.feomathar.adventofcode.util.parseInput

fun compute(fileName: String): Int {
    val lines = parseInput(fileName)[0].split(",").map { it.toInt() }.toMutableList()
//    lines[1] = 12
//    lines[2] = 2
//
//    return runProgram(lines)[0]

    for (i in 0..99) {
        for (j in 0..99) {
            lines[1] = i
            lines[2] = j
            if (runProgram(lines)[0] == 19690720) {
                return 100 * i + j
            }
        }
    }
    return -1
}

private fun runProgram(input: List<Int>): List<Int> {
    val instructions = input.toMutableList()
    var pc = 0
    while (instructions[pc] != 99) {
        val op = instructions[pc]
        if (op == 1) {
            instructions[instructions[pc + 3]] = instructions[instructions[pc + 1]] + instructions[instructions[pc + 2]]
        } else if (op == 2) {
            instructions[instructions[pc + 3]] = instructions[instructions[pc + 1]] * instructions[instructions[pc + 2]]
        } else {
            throw RuntimeException("UnexpectedOpCode")
        }
        pc += 4
    }
    return instructions
}
