package ch.feomathar.adventofcode.dayeleven

import ch.feomathar.adventofcode.intcode.Computer
import ch.feomathar.adventofcode.intcode.Exit
import ch.feomathar.adventofcode.util.Point
import ch.feomathar.adventofcode.util.parseInput

fun paint(fileName: String): Int {
    val code = parseInput(fileName)[0].split(",").map { it.toLong() }
    val computer = Computer(code)

    val paintedTiles = HashMap<Point, Int>()
    var direction = Point.Direction.UP   // 0 == up, 1 = r, 2 = d, 3 = l
    var current = Point.of(0, 0)
    paintedTiles[current] = 1

//    val testInput = LinkedList<String>()
//    testInput.addAll(arrayListOf("0", "0", "0", "0", "1", "0", "0"))
//    val testOut = LinkedList<String>()
//    testOut.addAll(arrayListOf("1", "0", "0", "0", "1", "0", "1", "0", "0", "1", "1", "0", "1", "0"))

    do {
        computer.addInput((paintedTiles[current] ?: 0).toString())
        val lastOp = computer.runProgram()
        if (lastOp == Exit) {
            break
        }
        paintedTiles[current] = computer.stdout.removeFirst().toInt()
        direction = direction.rotate(computer.stdout.removeFirst().toInt())
        current = current.getNeighbour(direction)
    } while (true)

    visualize(paintedTiles)

    return paintedTiles.keys.size
}

fun visualize(paintedTiles: HashMap<Point, Int>) {
    val xMin = paintedTiles.map { it.key.x }.min()!!
    val xMax = paintedTiles.map { it.key.x }.max()!!
    val yMin = paintedTiles.map { it.key.y }.min()!!
    val yMax = paintedTiles.map { it.key.y }.max()!!
    var result = ""
    for (i in yMax downTo yMin) {
        result += "\n"
        for (j in xMin..xMax) {
            result += if (paintedTiles[Point(j, i)] == 1) "#" else "."
        }
    }
    print(result)
}
