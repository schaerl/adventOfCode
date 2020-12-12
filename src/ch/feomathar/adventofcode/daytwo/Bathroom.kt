package ch.feomathar.adventofcode.daytwo

import ch.feomathar.adventofcode.util.parseInput
import kotlin.math.max
import kotlin.math.min

fun buttons(fileName: String): String {
    val lines = parseInput(fileName)
//    val keyPad = arrayOf(intArrayOf(1, 2, 3), intArrayOf(4, 5, 6), intArrayOf(7, 8, 9))
    val keyPad2 = arrayOf(
            arrayOf("0", "0", "1", "0", "0"),
            arrayOf("0", "2", "3", "4", "0"),
            arrayOf("5", "6", "7", "8", "9"),
            arrayOf("0", "A", "B", "C", "0"),
            arrayOf("0", "0", "D", "0", "0"))
    return lines
            .map { it.toCharArray().fold(Pair(2, 0), { acc, new -> newPosition(keyPad2, acc, new) }) }
            .map { keyPad2[it.first][it.second] }
            .fold("", { str, char -> str + char })
}

private fun newPosition(keypad: Array<Array<String>>, acc: Pair<Int, Int>, new: Char): Pair<Int, Int> {
    val newPos = when (new) {
        'D' -> Pair(min(4, acc.first + 1), acc.second)
        'R' -> Pair(acc.first, min(4, acc.second + 1))
        'U' -> Pair(max(0, acc.first - 1), acc.second)
        'L' -> Pair(acc.first, max(0, acc.second - 1))
        else -> throw RuntimeException("Unexpected direction")
    }
    return if (keypad[newPos.first][newPos.second] == "0") acc else newPos
}
