package ch.feomathar.adventofcode.util

import java.io.File

fun parseInput(fileName: String): List<String>{
    var result = ArrayList<String>()
    File(fileName).forEachLine { result.add(it) }
    return result
}
