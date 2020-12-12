package ch.feomathar.adventofcode.daythree

import ch.feomathar.adventofcode.util.parseInput
import kotlin.math.absoluteValue

fun findIntersect(fileName: String) : Int{
    val lines = parseInput(fileName)
    val wire1 = lines[0].split(",")
    val wire2 = lines[1].split(",")
    var current = Pair(0,0)
    val grid = HashMap<Pair<Int,Int>, MutableSet<Int>>()
    for(dir in wire1){
        val direction = dir[0]
        val amount = dir.substring(1).toInt()
        for(i in 1..amount){
            when (direction) {
                'U' -> current = Pair(current.first, current.second+1)
                'D' -> current = Pair(current.first, current.second-1)
                'R' -> current = Pair(current.first + 1, current.second)
                'L' -> current = Pair(current.first - 1, current.second)
            }
            grid.getOrPut(current, {HashSet()}).add(1)
        }
    }
    current = Pair(0,0)
    for(dir in wire2){
        val direction = dir[0]
        val amount = dir.substring(1).toInt()
        for(i in 1..amount){
            when (direction) {
                'U' -> current = Pair(current.first, current.second+1)
                'D' -> current = Pair(current.first, current.second-1)
                'R' -> current = Pair(current.first + 1, current.second)
                'L' -> current = Pair(current.first - 1, current.second)
            }
            grid.getOrPut(current, {HashSet()}).add(2)
        }
    }

    val intersects = grid.entries.filter { it.value.size > 1 }.map { it.key }
    val stepsToIntersections = ArrayList<Int>()
    for(intersect in intersects) {
        current = Pair(0, 0)
        var steps = 0
        outer@ for (dir in wire1) {
            val direction = dir[0]
            val amount = dir.substring(1).toInt()
            for (i in 1..amount) {
                when (direction) {
                    'U' -> current = Pair(current.first, current.second + 1)
                    'D' -> current = Pair(current.first, current.second - 1)
                    'R' -> current = Pair(current.first + 1, current.second)
                    'L' -> current = Pair(current.first - 1, current.second)
                }
                steps++
                if (current == intersect) {
                    break@outer
                }
            }
        }
        current = Pair(0, 0)
        outer@ for (dir in wire2) {
            val direction = dir[0]
            val amount = dir.substring(1).toInt()
            for (i in 1..amount) {
                when (direction) {
                    'U' -> current = Pair(current.first, current.second + 1)
                    'D' -> current = Pair(current.first, current.second - 1)
                    'R' -> current = Pair(current.first + 1, current.second)
                    'L' -> current = Pair(current.first - 1, current.second)
                }
                steps++
                if (current == intersect) {
                    break@outer
                }
            }
        }
        stepsToIntersections.add(steps)
    }
    return stepsToIntersections.sorted()[0]
}
