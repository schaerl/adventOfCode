package ch.feomathar.adventofcode.daythree

import ch.feomathar.adventofcode.util.parseInput
import java.lang.RuntimeException

fun deliver(fileName: String): Int{
    val instructions = parseInput(fileName)[0]
    var lastPos = Pair(0,0)
    var preLastPos = Pair(0,0)
    val visited = hashSetOf(lastPos, preLastPos)
    for (instruction in instructions) {
        lastPos = when(instruction){
            '^' -> Pair(lastPos.first-1, lastPos.second)
            '<' -> Pair(lastPos.first, lastPos.second-1)
            '>' -> Pair(lastPos.first, lastPos.second+1)
            'v' -> Pair(lastPos.first+1, lastPos.second)
            else -> throw RuntimeException("unexpected instruction")
        }
        visited.add(lastPos)
        lastPos = preLastPos.also { preLastPos = lastPos }
    }
    return visited.size
}
