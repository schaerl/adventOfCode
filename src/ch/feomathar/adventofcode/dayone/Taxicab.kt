package ch.feomathar.adventofcode.dayone

import ch.feomathar.adventofcode.util.parseInput
import kotlin.math.abs

fun execute(fileName: String): Int {
    val oneLine = parseInput(fileName)
    val lines = oneLine[0].split(", ")
    var dir = 0
//    val stepsInDir = intArrayOf(0,0,0,0) // steps in each direction. index 0 = N, 1: E, 2: S, 3:W
//    for (line in lines) {
//        val change = line[0]
//        val steps = line.substring(1).toInt()
//        if (change == 'L'){
//            dir = Math.floorMod(dir - 1, 4)
//        } else {
//            dir = (dir + 1) % 4
//        }
//        stepsInDir[dir] += steps
//    }
//
//    return stepsInDir[0] - stepsInDir[2] + stepsInDir[1] - stepsInDir[3]
    val pos = intArrayOf(0, 0)   // Position, up/down, right/left
    val visitedPositions = ArrayList<Pair<Int, Int>>()
    visitedPositions.add(Pair(pos[0], pos[1]))
    for (line in lines) {
        val change = line[0]
        val steps = line.substring(1).toInt()
        if (change == 'L') {
            dir = Math.floorMod(dir - 1, 4)
        } else {
            dir = (dir + 1) % 4
        }
        for (i in 1..steps) {
            when (dir) {
                0 -> pos[0]++
                1 -> pos[1]++
                2 -> pos[0]--
                3 -> pos[1]--
            }
            val newPair = Pair(pos[0], pos[1])
            if (visitedPositions.contains(newPair)) {
                return abs(newPair.first) + abs(newPair.second)
            } else {
                visitedPositions.add(newPair)
            }
        }
    }
    return -1
}


