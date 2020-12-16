package ch.feomathar.adventofcode.util

import kotlin.math.abs

class Point(val x: Int, val y: Int) : Comparable<Point> {

    fun getAllNeighbours(): List<Point> {
        val result = ArrayList<Point>()
        for (dir in Direction.values()) {
            result.add(getNeighbour(dir))
        }
        return result
    }

    fun getNeighbour(dir: Direction): Point {
        return getInDirection(dir, 1)
    }

    fun getInDirection(dir: Direction, amount: Int): Point {
        return when (dir) {
            Direction.RIGHT -> of(x + amount, y)
            Direction.UP -> of(x, y + amount)
            Direction.LEFT -> of(x - amount, y)
            Direction.DOWN -> of(x, y - amount)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Point

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

    override fun toString(): String {
        return "Point(x=$x, y=$y)"
    }


    companion object {
        fun of(x: Int, y: Int): Point {
            return Point(x, y)
        }
    }

    enum class Direction {
        UP, RIGHT, DOWN, LEFT;

        fun rotate(change: Int): Direction {
            return when (this) {
                UP -> if (change == 1) RIGHT else LEFT
                RIGHT -> if (change == 1) DOWN else UP
                DOWN -> if (change == 1) LEFT else RIGHT
                LEFT -> if (change == 1) UP else DOWN
            }
        }

        fun rotateDeg(i: Int): Direction {
            var newDir = this
            if (i < 0) {
                for (j in 1..abs(i)) {
                    newDir = newDir.rotate(0)
                }
            } else
                for (j in 1..abs(i)) {
                    newDir = newDir.rotate(1)
                }
            return newDir
        }
    }

    override fun compareTo(other: Point): Int {
        return if (y - other.y == 0) return x - other.x else y - other.y
    }

    operator fun component1(): Int {
        return x
    }

    operator fun component2(): Int {
        return y
    }

    operator fun minus(other: Point): Point {
        return of(x - other.x, y - other.y)
    }

    operator fun plus(other: Point): Point {
        return of(other.x + x, other.y + y)
    }

    operator fun times(other: Int): Point {
        return of(other * x, other * y)
    }
}
