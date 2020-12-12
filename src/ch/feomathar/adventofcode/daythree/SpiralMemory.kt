package ch.feomathar.adventofcode.daythree

import kotlin.math.pow

private val INPUT = 265149

fun findLoc(): Int {
    val ring = findRing(INPUT)
    var number = ringEnd(ring - 1) + 1
    var position = Pair(ring - 1, ring)
    val appendable = ringWidth(ring) - 1
    val side = (INPUT - number) / appendable
    val sideDifference = (INPUT - number) % appendable

    when (side) {
        0 -> position = Pair(position.first - sideDifference, position.second)
        1 -> position = Pair(position.first - appendable + 1, position.second - 1 - sideDifference)
        2 -> position = Pair(position.first - appendable + 1 + sideDifference + 1, position.second - appendable)
        3 -> position = Pair(position.first + 1, position.second - appendable + 1 + sideDifference)
    }

    return position.first + position.second
}

fun generateWeird(): Int {
    val map = HashMap<Pair<Int, Int>, Int>()
    var curr = Pair(0, 0)
    map[curr] = 1
    var dir = Dir.DOWN         // 0 :r, 1: u, 2: l, 3: d
    while (map[curr]!! < INPUT) {
        if (map[Pair(curr.first + dir.xOffCheck, curr.second + dir.yOffCheck)] == null) {
            dir = dir.getNextDir()
        }
        curr = Pair(curr.first + dir.xOffContinue, curr.second + dir.yOffContinue)
        map[curr] = calculateSumOfSurrounding(map, curr)
    }
    return map[curr]!!
}

fun calculateSumOfSurrounding(map: HashMap<Pair<Int, Int>, Int>, curr: Pair<Int, Int>): Int {
    var sum = 0
    for (i in -1..1) {
        for (j in -1..1) {
            if (i == 0 && j == 0) {
                continue
            }
            val neighbour = map[Pair(curr.first + i, curr.second + j)]
            if (neighbour != null) {
                sum += neighbour
            }
        }
    }
    return sum
}

fun findRing(input: Int): Int {
    var ring = 0
    while (input > ringEnd(ring)) {
        ring++
    }
    return ring
}

fun ringEnd(ring: Int): Int {
    return ringWidth(ring).toDouble().pow(2).toInt()
}

fun ringWidth(ring: Int): Int {
    return 2 * ring + 1
}

enum class Dir(val xOffCheck: Int, val yOffCheck: Int, val xOffContinue: Int, val yOffContinue: Int) {
    RIGHT(0, 1, 1, 0),
    UP(-1, 0, 0, 1),
    LEFT(0, -1, -1, 0),
    DOWN(1, 0, 0, -1);

    fun getNextDir(): Dir {
        return when (this) {
            RIGHT -> UP
            UP -> LEFT
            LEFT -> DOWN
            DOWN -> RIGHT
        }
    }
}
