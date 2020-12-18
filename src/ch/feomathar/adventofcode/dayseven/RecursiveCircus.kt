package ch.feomathar.adventofcode.dayseven

import ch.feomathar.adventofcode.ProgramError
import ch.feomathar.adventofcode.util.parseInput

private val PATTERN = Regex("(.*) \\((\\d+)\\)(?: -> (.*))?")

val totalWeightTree = HashMap<String, Int>()


fun findRoot(fileName: String): Int {
    val lines = parseInput(fileName)

    val reverseTree = HashMap<String, String>()
    val tree = HashMap<String, List<String>>()
    val processWeight = HashMap<String, Int>()
    for (line in lines) {
        val m = PATTERN.matchEntire(line)
        val g = m!!.groupValues
        if (g[3].isNotBlank()) {
            tree[g[1]] = g[3].split(", ")
            for (descendant in g[3].split(", ")) {
                reverseTree[descendant] = g[1]
            }
        } else {
            tree[g[1]] = ArrayList()
        }
        processWeight[g[1]] = g[2].toInt()
    }

    var current = reverseTree.toList().first().first

    while (reverseTree[current] != null) {
        current = reverseTree[current]!!
    }

    getTotalWeight(tree, current, processWeight)


//    return current
    val wrongProc = getWrongWeightedProc(tree, reverseTree, current, processWeight)
    val siblingWeights = tree[reverseTree[wrongProc]!!]!!.map { totalWeightTree[it]!! }
    val othersWeight = getMultiple(siblingWeights)
    val difference = othersWeight - totalWeightTree[wrongProc]!!
    return processWeight[wrongProc]!! + difference
}

fun getWrongWeightedProc(
    map: Map<String, List<String>>,
    reverseMap: Map<String, String>,
    of: String,
    weights: Map<String, Int>
): String {
    val childWeights = map[of]!!.map { totalWeightTree[it]!! }
    if (allMatch(childWeights)) {
        return of
    } else {
        val different = getDifferent(childWeights)
        for (proc in map[of]!!) {
            if (totalWeightTree[proc]!! == different) {
                return getWrongWeightedProc(map, reverseMap, proc, weights)
            }
        }
    }
    throw ProgramError("Something wrong")
}

fun getTotalWeight(map: Map<String, List<String>>, of: String, weights: Map<String, Int>): Int {
    if (totalWeightTree.containsKey(of)) {
        return totalWeightTree[of]!!
    }
    if (map[of]!!.isEmpty()) {
        totalWeightTree[of] = weights[of]!!
        return weights[of]!!
    } else {
        val calculated = map[of]!!.map { Pair(it, getTotalWeight(map, it, weights)) }
        calculated.forEach { totalWeightTree[it.first] = it.second }
        totalWeightTree[of] = calculated.map { it.second }.sum() + weights[of]!!
        return totalWeightTree[of]!!
    }
}

fun getDifferent(list: List<Int>): Int {
    return list.groupBy { it }.toList().first { it.second.size == 1 }.first
}

fun getMultiple(list: List<Int>): Int {
    return list.groupBy { it }.toList().first { it.second.size > 1 }.first
}

fun allMatch(list: List<Int>): Boolean {
    val init = list[0]
    for (i in list) {
        if (i != init) {
            return false
        }
    }
    return true
}

data class Process(val name: String, val weight: Int)
