package ch.feomathar.adventofcode.dayseven

import ch.feomathar.adventofcode.intcode.Computer
import ch.feomathar.adventofcode.intcode.Exit
import ch.feomathar.adventofcode.util.parseInput
import ch.feomathar.adventofcode.util.permute

fun findMaxInputs(fileName: String): Int {
    val input = parseInput(fileName)
    val code = input[0].split(",").map { it.toLong() }

    val phaseSettings = permute(arrayListOf(5, 6, 7, 8, 9))

    val results = ArrayList<Int>()
    for (setting in phaseSettings) {
        val computers = ArrayList<Computer>()
        for (value in setting) {
            val computer = Computer(code)
            computer.addInput(value.toString())
            computers.add(computer)
        }
        var prevOut = "0"
        var currentComputer = 0
        while (true) {
            val computer = computers[currentComputer % computers.size]
            computer.addInput(prevOut)
            val lastOp = computer.runProgram()
            prevOut = computer.stdout.removeLast()
            if (lastOp == Exit && currentComputer % computers.size == computers.size - 1) {
                break
                //we exited
            }
            currentComputer++
        }
        results.add(prevOut.toInt())
    }

//    val phaseSettings = permute(arrayListOf(0, 1, 2, 3, 4))
//
//    val results = ArrayList<Int>()
//    for (setting in phaseSettings) {
//        var prevOut = "0"
//        for (value in setting) {
//            val computer = Computer(code)
//            computer.addInput(value.toString())
//            computer.addInput(prevOut)
//            computer.runProgram()
//            prevOut = computer.stdout.removeLast()
//        }
//        results.add(prevOut.toInt())
//    }

    return results.max()!!
}

