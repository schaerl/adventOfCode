package ch.feomathar.adventofcode.daysix

import ch.feomathar.adventofcode.util.parseInput

private val PATTERN = Regex("(.*)\\)(.*)")

fun countOrbits(fileName: String): Int {
    var lines = parseInput(fileName)
    val reachesMap = HashMap<String, MutableList<String>>()
    for (line in lines) {
        val g = PATTERN.matchEntire(line)!!.groupValues
        reachesMap[g[1]] = reachesMap.getOrDefault(g[1], ArrayList())
        reachesMap[g[1]]!!.add(g[2])
    }
//    return getOrbits(reachesMap, "COM", 0)
    val descendantsMap = findDescendants(reachesMap, "COM", 0)
    val latestDescendant = descendantsMap.filter { it.value.map { it.second }.contains("YOU") && it.value.map { it.second }.contains("SAN") }
            .toList().sortedBy { -it.first.first }[0]
    val santaDepthFromDescendant = latestDescendant.second.filter { it.second == "SAN" }.first().first - latestDescendant.first.first - 1
    val youDepthFromDescendant = latestDescendant.second.filter { it.second == "YOU" }.first().first - latestDescendant.first.first - 1
    return santaDepthFromDescendant + youDepthFromDescendant
}

fun getOrbits(map: Map<String, List<String>>, body: String, depth: Int): Int {
    return if (map[body] == null) {
        depth
    } else {
        var sum = depth
        for (other in map[body]!!) {
            sum += getOrbits(map, other, depth + 1)
        }
        sum
    }
}

fun findDescendants(map: HashMap<String, MutableList<String>>, from: String, depth: Int): Map<Pair<Int, String>, List<Pair<Int, String>>> {
    return if (map[from] == null) {
        hashMapOf(Pair(Pair(depth, from), ArrayList()))
    } else {
        val all = HashMap<Pair<Int, String>, List<Pair<Int, String>>>()
        val descendants = ArrayList<Pair<Int, String>>()
        for (other in map[from]!!) {
            val desc = findDescendants(map, other, depth + 1)
            for (entry in desc) {
                all[entry.key] = entry.value
                descendants.add(entry.key)
            }
            all[Pair(depth, from)] = descendants
        }
        return all
    }
}
