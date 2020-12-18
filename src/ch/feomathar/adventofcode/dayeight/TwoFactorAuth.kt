package ch.feomathar.adventofcode.dayeight

import ch.feomathar.adventofcode.util.modulus
import ch.feomathar.adventofcode.util.parseInput

private val ROTATE_PATTERN = Regex("rotate (.*) [x|y]=(\\d+) by (\\d+)")

fun pixelCount(fileName: String): String {
    val lines = parseInput(fileName)
    val instructions = lines.map { mapInstruction(it) }

    val display = Array(6) { Array(50) { false } }

    instructions.forEach { it.execute(display) }
    return visualize(display)
}

fun mapInstruction(it: String): Instruction {
    return if (it.startsWith("rect ")) {
        val (x, y) = it.substring(5).split("x").map { it.toInt() }
        Light(x, y)
    } else {
        val g = ROTATE_PATTERN.matchEntire(it)!!.groupValues
        Rotate(g[1], g[2].toInt(), g[3].toInt())
    }
}

fun visualize(grid: Array<Array<Boolean>>): String {
    var result = ""
    for (row in grid) {
        for (char in row) {
            result += if (char) "#" else " "
        }
        result += "\n"
    }
    return result
}

abstract class Instruction() {
    abstract fun execute(grid: Array<Array<Boolean>>)
}

class Rotate(private val direction: String, private val index: Int, private val amount: Int) : Instruction() {
    override fun execute(grid: Array<Array<Boolean>>) {
        if (direction == "row") {
            val newRow = Array(50) { false }
            for (i in grid[index].indices) {
                newRow[i] = grid[index][(i - amount).modulus(50)]
            }
            grid[index] = newRow
        } else {
            val origValues = ArrayList<Boolean>()
            for (row in grid) {
                origValues.add(row[index])
            }
            for (i in grid.indices) {
                grid[(i + amount).modulus(6)][index] = origValues[i]
            }
        }
    }
}

class Light(val x: Int, val y: Int) : Instruction() {
    override fun execute(grid: Array<Array<Boolean>>) {
        for (i in 0 until y) {
            for (j in 0 until x) {
                grid[i][j] = true
            }
        }
    }
}
