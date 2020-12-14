package ch.feomathar.adventofcode.dayfive

import ch.feomathar.adventofcode.util.parseInput
import java.util.*
import kotlin.math.abs

fun react(fileName: String): Int {
    val input = parseInput(fileName)
    val inputs = input[0]

    return doReact(inputs)
}

private fun doReact(input: String): Int {
    val output = Stack<Char>()
    for (c in input) {
        if (output.size > 0 && output.peek() != null && abs(output.peek() - c) == 32) {
            output.pop()
        } else {
            output.push(c)
        }
    }
    return output.size
}

fun reduction(fileName: String): Int {
    val input = parseInput(fileName)
    val process = input[0]
    val results = ArrayList<Int>()
    for (removed in process.toCharArray().distinct()) {
        val newInput = process.filter { abs(removed - it) != 32 && abs(removed - it) != 0 }
        results.add(doReact(newInput))
    }
    return results.min()!!
}
