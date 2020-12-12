package ch.feomathar.adventofcode.dayone

import ch.feomathar.adventofcode.util.parseInput

fun drift(fileName: String) : Int{
    val lines = parseInput(fileName)
    val parsed = lines.map { it.toInt() }
//    return parsed.sum()
    val reached = HashSet<Int>()
    var frequency = 0
    var i = 0
    while(true) {
        frequency += parsed[i];
        if(!reached.add(frequency)){
            return frequency
        }
        i = (i+1) % parsed.size
    }
}
