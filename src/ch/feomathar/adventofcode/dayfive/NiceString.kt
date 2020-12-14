package ch.feomathar.adventofcode.dayfive

import ch.feomathar.adventofcode.util.parseInput

private val FORBIDDEN = arrayOf("ab", "cd", "pq", "xy")
private val VOWELS = arrayOf('a', 'e', 'i', 'o', 'u')
fun findNice(fileName: String): Int {
    val strings = parseInput(fileName)
    return strings.filter { isNewNice(it) }.count()
}

fun isNice(string: String): Boolean {
    var double = false
    var vowels = 0
    for (i in string.indices) {
        if (VOWELS.contains(string[i])) {
            vowels++
        }
        if (i + 1 >= string.length) {
            break;
        }
        val two = string.subSequence(i, i + 2)
        if (FORBIDDEN.contains(two)) {
            return false
        }
        if (two[0] == two[1]) {
            double = true
        }
    }
    return double && vowels >= 3
}

fun isNewNice(string: String): Boolean {
    var prev = '0'
    var prePrev = '0'
    var current = '0'
    val doubles = HashMap<String, MutableList<Int>>()
    var skips = false
    for (i in string.indices) {
        current = string[i]
        val duo = String(charArrayOf(prev, current))
        doubles[duo] = doubles.getOrDefault(duo, ArrayList())
        doubles[duo]?.add(i - 1)
        if (prePrev == current) {
            skips = true
        }

        prePrev = prev
        prev = current
    }
    val hasValidDoubles = doubles.toList().filter { it.second.size > 1 }.filter { it.second.max()!! - it.second.min()!! > 1 }.any()
    return hasValidDoubles && skips
//    return doubles.isNotEmpty() && skips
}
