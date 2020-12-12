package ch.feomathar.adventofcode.dayone

import ch.feomathar.adventofcode.util.parseInput

fun fuel(fileName: String): Int {
    val lines = parseInput(fileName)
    val parsed = lines.map { it.toInt() }
//    return parsed.map { getFuelAmount(it) }.sum()
    return parsed.map { extensiveFuelAmount(it) }.sum()
}

private fun extensiveFuelAmount(weight: Int): Int {
    var newFuel = getFuelAmount(weight);
    var totalFuel = 0
    while (newFuel > 0) {
        totalFuel += newFuel
        newFuel = getFuelAmount(newFuel)
    }
    return totalFuel
}

private fun getFuelAmount(weight: Int): Int {
    return weight / 3 - 2
}
