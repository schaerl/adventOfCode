package ch.feomathar.adventofcode.dayfour

import ch.feomathar.adventofcode.util.parseInput

fun countValid(fileName: String): Int {
    val lines = parseInput(fileName)
    val passphraseWords = lines.map { it.split(" ") }
    return passphraseWords.filter { it.distinctBy { it.toCharArray().sorted() }.size == it.size }.count()
}
