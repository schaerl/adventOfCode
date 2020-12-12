package ch.feomathar.adventofcode.daythree

import ch.feomathar.adventofcode.util.parseInput

fun possible(fileName:String) : Int {
//    val triangleSides = parseInput(fileName).map { it.split(Regex("\\s")).filter { it.isNotBlank() }.map { it.toInt() }.sorted() }
    val temp = ArrayList<Int>()
    val triangleSidesFlipped = parseInput(fileName).map { it.split(Regex("\\s")).filter { it.isNotBlank() }.map { it.toInt() }}
    for (i in triangleSidesFlipped[0].indices){
        for (j in triangleSidesFlipped.indices){
            temp.add(triangleSidesFlipped[j][i])
        }
    }
    val triangleSides = temp.chunked(3)
    return triangleSides.map { it.sorted() }.map { it[0] + it[1] > it[2] }.filter { it }.count()
}
