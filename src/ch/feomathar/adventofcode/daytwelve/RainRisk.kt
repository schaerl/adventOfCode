package ch.feomathar.adventofcode.daytwelve

import ch.feomathar.adventofcode.ProgramError
import ch.feomathar.adventofcode.util.Point
import ch.feomathar.adventofcode.util.parseInput
import kotlin.math.abs

private val PATTERN = Regex("(\\w)([0-9]+)")

fun getDist(fileName: String): Int {
    val lines = parseInput(fileName)
    val parsed = lines.map { PATTERN.matchEntire(it)!!.groupValues }.map { Pair(it[1], it.last().toInt()) }
    var currentShip = Point.of(0, 0)
    var currentWay = Point.of(10, 1)
    var direction = Point.Direction.RIGHT

//    for (parse in parsed) {
//        when (parse.first) {
//            "N" -> current = Point(current.x, current.y + parse.second)
//            "E" -> current = Point(current.x + parse.second, current.y)
//            "S" -> current = Point(current.x - parse.second, current.y)
//            "W" -> current = Point(current.x, current.y - parse.second)
//            "L" -> direction = direction.rotateDeg(-parse.second / 90)
//            "R" -> direction = direction.rotateDeg(parse.second / 90)
//            "F" -> current = current.getInDirection(direction, parse.second)
//            else -> throw ProgramError("Invalid direction")
//        }
//    }

    for (parse in parsed) {
        when (parse.first) {
            "N" -> currentWay = currentWay.getInDirection(Point.Direction.UP, parse.second)
            "E" -> currentWay = currentWay.getInDirection(Point.Direction.RIGHT, parse.second)
            "S" -> currentWay = currentWay.getInDirection(Point.Direction.DOWN, parse.second)
            "W" -> currentWay = currentWay.getInDirection(Point.Direction.LEFT, parse.second)
            "L" -> {
                for (i in 1..parse.second / 90) {
                    val diff = currentWay - currentShip
                    currentWay = Point(currentShip.x - diff.y, currentShip.y + diff.x)
                }
            }
            "R" -> {
                for (i in 1..parse.second / 90) {
                    val diff = currentWay - currentShip
                    currentWay = Point(currentShip.x + diff.y, currentShip.y - diff.x)
                }
            }
            "F" -> {
                val diff = (currentWay - currentShip)
                currentShip = currentShip + diff * parse.second
                currentWay = currentShip + diff
            }
            else -> throw ProgramError("Invalid direction")
        }
    }

    return abs(currentShip.x) + abs(currentShip.y)
}
