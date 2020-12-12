package ch.feomathar.adventofcode.dayfour

import ch.feomathar.adventofcode.ProgramError
import ch.feomathar.adventofcode.util.parseInput

private val PATTERN = Regex("\\[(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2})] (.*)")
private val INFO_MATCH = Regex(".*#(\\d+).*")
fun findSleepy(fileName: String): Int {
    val lines = parseInput(fileName)
    val parsed = lines.map { Line.parse(it) }.sorted()
    var currentId = 0
    var sleepStart = 0
    val sleepedOn = HashMap<Int, IntArray>()
    for (line in parsed) {
        val m = INFO_MATCH.matchEntire(line.info)
        if (m != null && m.groupValues.isNotEmpty()) {
            currentId = m.groupValues[1].toInt()
        } else if (line.info.contains("falls")) {
            sleepStart = line.min
        } else if (line.info.contains("wakes")) {
            for (i in sleepStart until line.min) {
                val minutes = sleepedOn.getOrDefault(currentId, IntArray(60))
                minutes[i] += 1
                sleepedOn[currentId] = minutes
            }
        }
    }
//    return sleepedOn.toList().sortedBy { -it.second.sum() }.map { entry -> entry.first * entry.second.indices.maxBy { entry.second[it] }!! }.first()
    return sleepedOn.toList().sortedBy { -it.second.max()!! }.map { entry -> entry.first * entry.second.indices.maxBy { entry.second[it] }!! }.first()
}

data class Line(val year: Int, val month: Int, val day: Int, val hour: Int, val min: Int, val info: String) : Comparable<Line> {


    companion object {
        fun parse(line: String): Line {
            val m = PATTERN.matchEntire(line) ?: throw ProgramError("Invalid input")
            val g = m.groupValues
            return Line(g[1].toInt(), g[2].toInt(), g[3].toInt(), g[4].toInt(), g[5].toInt(), g[6])
        }
    }

    override fun compareTo(other: Line) = compareValuesBy(this, other, { it.year }, { it.month }, { it.day }, { it.hour }, { it.min })
}
