package ch.feomathar.adventofcode.daysix

import ch.feomathar.adventofcode.util.Point
import ch.feomathar.adventofcode.util.parseInput
import kotlin.math.abs

fun largestFinite(fileName: String): Int {
    val lines = parseInput(fileName)
    var points = lines.map { Point(it.split(", ")[0].toInt(), it.split(", ")[1].toInt()) }
    val minPoint = points.min()!!
    val maxPoint = points.max()!!
    val infinte = HashSet<Int>()
    val border = HashMap<Point, Int>()
    val tempBorder = HashMap<Point, MutableSet<Int>>()
    points.withIndex().forEach { border[it.value] = it.index }

    val availablePoints = buildGrid(points)
    val populatedPoints = HashMap<Point, Int>()
    while (availablePoints.isNotEmpty()) {
        for (point in border) {
            if (isBoundingBox(point.key, minPoint, maxPoint)) {
                infinte.add(point.value)
            }
            val allNeighbours = point.key.getAllNeighbours().toSet()
            for (availNeigh in allNeighbours) {
                if (availablePoints[availNeigh] == null) {
                    continue
                }
                tempBorder[availNeigh] = tempBorder.getOrDefault(availNeigh, HashSet())
                tempBorder[availNeigh]!!.add(point.value)
            }
        }
        val fixedNewBorder =
            tempBorder.filter { it.value.size == 1 }
                .map { Pair(it.key, it.value.first()) } // The new border does not contain overlapping points
        // Remove points
        border.map { it.key }.forEach { availablePoints.remove(it) }
        tempBorder.keys.forEach { availablePoints.remove(it) }
        populatedPoints.putAll(border)
        populatedPoints.putAll(fixedNewBorder)
        border.clear()
        fixedNewBorder.forEach { border[it.first] = it.second }
    }

    return populatedPoints.toList().filter { !infinte.contains(it.second) }.groupBy { it.second }.toList()
        .map { it.second.size }.max()!!
}

fun part2(fileName: String): Int {
    val lines = parseInput(fileName)
    var points = lines.map { Point(it.split(", ")[0].toInt(), it.split(", ")[1].toInt()) }
    val availablePoints = buildGrid(points)
    val valids = ArrayList<Point>()
    for (point in availablePoints) {
        var sum = 0
        for (coordinate in points) {
            val diff = coordinate - point.key
            sum += abs(diff.x) + abs(diff.y)
        }
        if (sum < 10000) {
            valids.add(point.key)
        }
    }
    return valids.size
}

fun buildGrid(points: List<Point>): MutableMap<Point, Boolean> {
    val result = HashMap<Point, Boolean>()
    val minY = points.map { it.y }.min()!!
    val maxY = points.map { it.y }.max()!!
    val minX = points.map { it.x }.min()!!
    val maxX = points.map { it.x }.max()!!
    for (i in minX..maxX) {
        for (j in minY..maxY) {
            result[Point.of(i, j)] = true
        }
    }
    return result
}

private fun isBoundingBox(check: Point, min: Point, max: Point): Boolean {
    return (check.x == min.x || check.y == min.y || check.x == max.x || check.y == max.y)
}
