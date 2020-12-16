package ch.feomathar.adventofcode.daythirteen

import ch.feomathar.adventofcode.intcode.Computer
import ch.feomathar.adventofcode.intcode.Exit
import ch.feomathar.adventofcode.util.Point
import ch.feomathar.adventofcode.util.parseInput

fun countTiles(fileName: String): Int {
    var code = parseInput(fileName)[0].split(",").map { it.toLong() }
    val computer = Computer(code)

    val tiles = ArrayList<Pair<Point, Int>>()

//    computer.runProgram()
//    computer.stdout.chunked(3).forEach { tiles.add(Pair(Point.of(it[0].toInt(), it[1].toInt()), it[2].toInt())) }
//    return tiles.filter { it.second == 2 }.count()

    return play(computer)
}

fun play(computer: Computer): Int {
    computer.setByte(0, 2)
    computer.addInput("0")
    val grid = HashMap<Point, String>()

    do {
        computer.stdout.clear()
        val lastOp = computer.runProgram()
        for (dots in computer.stdout.chunked(3)) {
            grid[Point(dots[0].toInt(), dots[1].toInt())] = dots[2]
        }
        visualize(grid)
        computer.addInput(findJoyDir(computer.stdout))
    } while (lastOp != Exit)

    return computer.stdout.chunked(3).first { it[0] == "-1" && it[1] == "0" }[2].toInt()
}

fun findJoyDir(stdout: ArrayDeque<String>): String {
    return try {
        val triples = stdout.chunked(3)
        val xBall = triples.first { it[2].toInt() == 4 }[0].toInt()
        val xPaddle = triples.first { it[2].toInt() == 3 }[0].toInt()
        xBall.compareTo(xPaddle).toString()
    } catch (e: Exception) {
        "0"
    }
}

fun visualize(grid: HashMap<Point, String>) {
    var index = 0
    var result = ""
    for (point in grid.toList().sortedBy { it.first }) {
        if (point.first.x == -1) {
            result += point.second + "\n"
            continue
        } else if (point.first.y != index) {
            result += "\n"
            index = point.first.y
        }
        result += when (point.second) {
            "0" -> "."
            else -> point.second
        }
    }
    print(result)
    println()
}
