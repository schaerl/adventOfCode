package ch.feomathar.adventofcode.daythree

import ch.feomathar.adventofcode.ProgramError
import ch.feomathar.adventofcode.util.parseInput

fun findIntersect(fileName: String): Int {
    val lines = parseInput(fileName)
    val wire1 = lines[0].split(",")
    val wire2 = lines[1].split(",")
    val grid = HashMap<Pair<Int, Int>, MutableSet<Int>>()
    appendToGrid(grid, wire1, Pair(0, 0), 1)
    appendToGrid(grid, wire2, Pair(0, 0), 2)

    val intersects = grid.entries.filter { it.value.size > 1 }.map { it.key }
    val stepsToIntersections = ArrayList<Int>()
    for (intersect in intersects) {
        var steps = countStepsToPositon(intersect, wire1)
        steps += countStepsToPositon(intersect, wire2)
        stepsToIntersections.add(steps)
    }
    return stepsToIntersections.sorted()[0]
}

fun countStepsToPositon(target: Pair<Int, Int>, wire: List<String>): Int {
    var current = Pair(0, 0)
    var steps = 0
    for (dir in wire) {
        val direction = dir[0]
        val amount = dir.substring(1).toInt()
        for (i in 1..amount) {
            current = getNextPosition(current, direction)
            steps++
            if (current == target) {
                return steps
            }
        }
    }
    return steps
}

fun appendToGrid(grid: HashMap<Pair<Int, Int>, MutableSet<Int>>, wire: List<String>, startPos: Pair<Int, Int>, token: Int) {
    var current = startPos
    for (dir in wire) {
        val direction = dir[0]
        val amount = dir.substring(1).toInt()
        for (i in 1..amount) {
            current = getNextPosition(current, direction)
            grid.getOrPut(current, { HashSet() }).add(token)
        }
    }
}

fun getNextPosition(current: Pair<Int, Int>, direction: Char): Pair<Int, Int> {
    return when (direction) {
        'U' -> Pair(current.first, current.second + 1)
        'D' -> Pair(current.first, current.second - 1)
        'R' -> Pair(current.first + 1, current.second)
        'L' -> Pair(current.first - 1, current.second)
        else -> throw ProgramError("Invalid direction!")
    }
}
