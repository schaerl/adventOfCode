package ch.feomathar.adventofcode.daythree

import ch.feomathar.adventofcode.util.parseInput

private val PATTERN = Regex("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)")

fun findOverlaps(fileName: String): Int {

    val lines = parseInput(fileName)
    val claims = lines.map { Claim.parse(it) }

    val cloth = MutableList(1000) { MutableList(1000) { HashSet<Int>() } }

    val intercepts = HashSet<Int>()
    claims.forEach {
        for (i in it.x until it.x + it.width) {
            for (j in it.y until it.y + it.height) {
                cloth[i][j].add(it.id)
                if (cloth[i][j].size > 1) {
                    intercepts.addAll(cloth[i][j])
                }
            }
        }
    }

    return claims.map { it.id }.filter { !intercepts.contains(it) }[0]
}

class Claim(val id: Int, val x: Int, val y: Int, val width: Int, val height: Int) {
    companion object {
        fun parse(data: String): Claim {
            val match = PATTERN.matchEntire(data)
            val g = match!!.groupValues
            return Claim(g[1].toInt(), g[2].toInt(), g[3].toInt(), g[4].toInt(), g[5].toInt())
        }
    }
}
