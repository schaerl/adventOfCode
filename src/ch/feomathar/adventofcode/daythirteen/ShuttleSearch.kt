package ch.feomathar.adventofcode.daythirteen

import ch.feomathar.adventofcode.util.parseInput

fun findNearest(fileName: String): Long {
    val lines = parseInput(fileName)
    val estimate = lines[0].toInt()
//    val ids = lines[1].split(",").filter { it != "x" }.map { it.toInt() }
//    return ids.map { (estimate / it + 1) * it % estimate * it }.min()!!

    val indexedIds = lines[1].split(",").withIndex().filter { it.value != "x" }
    var initial = 10000000000000L
    var factor = 1L
    for (tuple in indexedIds) {
        val modulus = tuple.value.toLong()
        val ind = tuple.index
        val standardizedOffset = ind % modulus    // If index is larger than index, we ni
        while ((initial % modulus) != (modulus - standardizedOffset) % modulus) {
            initial += factor
        }
        print("")
        factor *= tuple.value.toLong()
    }

    return initial.toLong()
}
