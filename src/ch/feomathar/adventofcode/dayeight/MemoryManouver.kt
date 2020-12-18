package ch.feomathar.adventofcode.dayeight

import ch.feomathar.adventofcode.util.parseInput

fun sumMetaData(fileName: String): Int {
    val lines = parseInput(fileName)
    val numbers = lines[0].split(" ").map { it.toInt() }

    return getValue(0, numbers).first
}

/**
 * returns sum and index of next node
 */
fun getMetaDataSum(index: Int, tree: List<Int>): Pair<Int, Int> {
    val atIndex = tree[index]
    if (atIndex == 0) {
        val nextNodeIndex = index + tree[index + 1] + 2
        return Pair(tree.subList(index + 2, nextNodeIndex).sum(), nextNodeIndex)
    } else {
        var sum = 0
        var nextInd = index + 2
        for (i in 0 until atIndex) {
            val meta = getMetaDataSum(nextInd, tree)
            sum += meta.first
            nextInd = meta.second
        }

        val nextNodeIndex = nextInd + tree[index + 1]
        return Pair(tree.subList(nextInd, nextNodeIndex).sum() + sum, nextNodeIndex)
    }
}

fun getValue(index: Int, tree: List<Int>): Pair<Int, Int> {
    val atIndex = tree[index]
    if (atIndex == 0) {
        val nextNodeIndex = index + tree[index + 1] + 2
        return Pair(tree.subList(index + 2, nextNodeIndex).sum(), nextNodeIndex)
    } else {
        val values = ArrayList<Int>()
        var nextInd = index + 2
        for (i in 0 until atIndex) {
            var value = getValue(nextInd, tree)
            values.add(value.first)
            nextInd = value.second
        }
        val nextNodeIndex = nextInd + tree[index + 1]
        val actualValue =
            tree.subList(nextInd, nextNodeIndex).map { if (values.indices.contains(it - 1)) values[it - 1] else 0 }
                .sum()
        return Pair(actualValue, nextNodeIndex)
    }
}
