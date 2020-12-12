package ch.feomathar.adventofcode.dayfour

private val MIN = 246515
private val MAX = 739105

fun findAllPws(): Int {
    return (MIN..MAX).filter { checkValid(it) }.count()
}

private fun checkValid(toCheck: Int): Boolean {
    val splitup = toCheck.toString().chunked(1).map { it.toInt() }
    var doubles = HashMap<Int, Int>()
    for (i in 0 until splitup.size - 1) {
        if (splitup[i] == splitup[i + 1]) {
            doubles[splitup[i]] = doubles.getOrDefault(splitup[i], 1) + 1
        }
        if (splitup[i] > splitup[i + 1]) {
            return false
        }
    }
    return doubles.map { it.value }.contains(2)
}
