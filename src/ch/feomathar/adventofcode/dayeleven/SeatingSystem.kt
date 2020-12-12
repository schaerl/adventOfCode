package ch.feomathar.adventofcode.dayeleven

import ch.feomathar.adventofcode.util.parseInput
import kotlin.math.min

fun determine(fileName: String): Int {
    val lines = parseInput(fileName)
    var floorRows = lines.map { it.toCharArray().map { Tile.parse(it) } }
    var oldLists = HashSet<List<List<Tile>>>()
    do {
        val oldList = deepCopyTileList(floorRows)
        if (!oldLists.add(oldList)) {
            print("LOOP")
        }
        floorRows = simulate(floorRows)
    } while (oldList != floorRows)

    return floorRows.flatten().filter { it == Tile.TAKEN }.count()
}

private fun simulate(floorRows: List<List<Tile>>): List<List<Tile>> {
    val calcSet = floorRows.toMutableList().map { it.toMutableList() }
    for (i in floorRows.indices) {
        for (j in floorRows[i].indices) {
            calcSet[i][j] = getNewState(floorRows, i, j)
        }
    }
    return calcSet
}

fun getNewState(floorRows: List<List<Tile>>, i: Int, j: Int): Tile {
    val tile = floorRows[i][j]
    if (tile == Tile.FLOOR) {
        return Tile.FLOOR
    }
    val surroundingSeats = getTakenSurroundingSeatsComplex(floorRows, i, j)
    return if (tile == Tile.FREE && surroundingSeats == 0) {
        Tile.TAKEN
    } else if (tile == Tile.TAKEN && surroundingSeats >= 5) {
        Tile.FREE
    } else {
        tile
    }
}

fun getTakenSurroundingSeats(floorRows: List<List<Tile>>, i: Int, j: Int): Int {
    var seats = 0
    for (yOff in -1..1) {
        for (xOff in -1..1) {
            val yInd = i + yOff
            val xInd = j + xOff
            // If yOff & xOff == 0 -> Would count itself, or index out of bounds
            if ((yOff == 0 && xOff == 0) || (0 > yInd || 0 > xInd || yInd >= floorRows.size || xInd >= floorRows[i].size)) {
                continue
            } else {
                if (floorRows[yInd][xInd] == Tile.TAKEN) {
                    seats++
                }
            }
        }
    }
    return seats
}

fun getTakenSurroundingSeatsComplex(floorRows: List<List<Tile>>, i: Int, j: Int): Int {
    var seats = 0
    seats += getSeatsFromList(floorRows[i], j)      // row

    val column = floorRows.map { it[j] }.toList()
    seats += getSeatsFromList(column, i)           // column

    val diag = buildForwardDiag(floorRows, i, j)
    seats += getSeatsFromList(diag, min(i, j))       // diag1
    val (diag2, index) = buildBackwardDiag(floorRows, i, j)
    seats += getSeatsFromList(diag2, index)        // diag2
    return seats
}

private fun buildForwardDiag(floorRows: List<List<Tile>>, i: Int, j: Int): ArrayList<Tile> {
    val diag = ArrayList<Tile>()
    for (y in floorRows.indices) {
        for (x in floorRows[i].indices) {
            if (y - x == i - j) {
                diag.add(floorRows[y][x])
            }
        }
    }
    return diag
}

private fun buildBackwardDiag(floorRows: List<List<Tile>>, i: Int, j: Int): Pair<ArrayList<Tile>, Int> {
    val diag = ArrayList<Tile>()
    var index = 0
    for (y in floorRows.indices) {
        for (x in floorRows[i].indices) {
            if (i - y == x - j) {
                diag.add(floorRows[y][x])
                if (y == i) {
                    index = diag.size - 1
                }
            }
        }
    }
    return Pair(diag, index)
}

fun getSeatsFromList(floorRow: List<Tile>, index: Int): Int {
    var seats = 0
    for (check in index - 1 downTo 0) {
        val tile = floorRow[check]
        if (tile == Tile.TAKEN) {
            seats++
            break
        } else if (tile == Tile.FREE) {
            break
        }
    }
    for (check in index + 1 until floorRow.size) {
        if (floorRow[check] == Tile.TAKEN) {
            seats++
            break
        } else if (floorRow[check] == Tile.FREE) {
            break
        }
    }
    return seats
}

private fun deepCopyTileList(floorRows: List<List<Tile>>): List<List<Tile>> {
    val baseList = ArrayList<List<Tile>>()
    for (floorRow in floorRows) {
        baseList.add(ArrayList(floorRow))
    }
    return baseList
}

enum class Tile {
    TAKEN, FREE, FLOOR;

    companion object {
        fun parse(char: Char): Tile {
            if (char == '.') {
                return FLOOR
            } else if (char == '#') {
                return TAKEN
            } else if (char == 'L') {
                return FREE
            } else {
                throw RuntimeException("Unexpected input")
            }
        }
    }
}
