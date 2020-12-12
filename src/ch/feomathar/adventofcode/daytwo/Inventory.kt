package ch.feomathar.adventofcode.daytwo

import ch.feomathar.adventofcode.util.parseInput
import java.lang.RuntimeException

fun calculate(fileName: String): Int{
    val lines = parseInput(fileName)
    val amountOfCharacters = lines
            .map { it.groupBy { char->char}}
            .map { it.values
                    .map { it.size }
                    .distinct()
            }
    return amountOfCharacters.fold(0, {acc, newVals -> if (newVals.contains(2)) acc+1 else acc }) *
            amountOfCharacters.fold(0, {acc, newVals -> if (newVals.contains(3)) acc+1 else acc })
}

fun findOneDiff(fileName: String): String {
    val lines = parseInput(fileName)
    for (i in lines.indices) {
        for (j in i until lines.size) {
            val index = oneDiff(lines[i], lines[j])
            if (index != -1){
                return lines[i].removeRange(index,index+1)
            }
        }
    }
    throw RuntimeException("No Match found")
}

fun oneDiff(a: String, b: String): Int {
    var difference = -1
    for (i in a.indices) {
        if (a[i] != b[i]) {
            if (difference != -1) {
                return -1
            } else {
                difference = i
            }
        }
    }
    return difference
}
