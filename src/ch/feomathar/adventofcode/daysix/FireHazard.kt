package ch.feomathar.adventofcode.daysix

import ch.feomathar.adventofcode.util.parseInput
import kotlin.math.max

private val PATTERN = Regex("(.*) (\\d+),(\\d+) through (\\d+),(\\d+)")


fun toggle(fileName: String): Int {
    val instructions = parseInput(fileName)
    val parsed = instructions.map { parse(it) }
    val lights = Array(1000) { Array(1000) { 0 } }

    for (ins in parsed) {
        for (i in ins.xStart..ins.xEnd) {
            for (j in ins.yStart..ins.yEnd) {
                when (ins.what) {
                    Toggle.ON -> lights[i][j]++
                    Toggle.OFF -> lights[i][j] = max(lights[i][j] - 1, 0)
                    Toggle.TOGGLE -> lights[i][j] += 2
                }
            }
        }
    }

    return lights.flatten().sum()
}

private fun parse(string: String): Ins {
    val m = PATTERN.matchEntire(string)
    val g = m!!.groupValues
    return Ins(g[2].toInt(), g[3].toInt(), g[4].toInt(), g[5].toInt(), Toggle.parse(g[1]))
}

data class Ins(val xStart: Int, val yStart: Int, val xEnd: Int, val yEnd: Int, val what: Toggle)

enum class Toggle {
    ON, OFF, TOGGLE;

    companion object {
        fun parse(string: String): Toggle {
            return when {
                string.endsWith("on") -> ON
                string.endsWith("off") -> OFF
                else -> TOGGLE
            }
        }
    }
}
