package ch.feomathar.adventofcode.daysixteen

import ch.feomathar.adventofcode.util.parseInput

private val RESTRICTIONS = Regex("(.*): (\\d+)-(\\d+) or (\\d+)-(\\d+)")

private val restrictions = HashMap<String, MutableList<IntRange>>()

fun findValids(fileName: String): Long {
    val lines = parseInput(fileName)

    for (line in lines) {
        if (line.isBlank()) {
            break
        }
        val g = RESTRICTIONS.matchEntire(line)!!.groupValues
        restrictions[g[1]] = restrictions.getOrDefault(g[1], ArrayList())
        restrictions[g[1]]!!.add(g[2].toInt()..g[3].toInt())
        restrictions[g[1]]!!.add(g[4].toInt()..g[5].toInt())
    }

    val index = lines.indexOf("nearby tickets:")
    val tickets = lines.subList(index + 1, lines.size)
//    var sum = 0
    val invalidTickets = ArrayList<String>()
    tickets@ for (ticket in tickets) {
        val values = ticket.split(",").map { it.toInt() }
        for (value in values) {
            val isContained =
                restrictions.values.flatten().fold(false, { valid, range -> valid || range.contains(value) })
            if (!isContained) {
//                sum += value
                invalidTickets.add(ticket)
                continue@tickets
            }
        }
    }

    val validTickets = ArrayList(tickets)
    validTickets.removeAll(invalidTickets)

    val myTicket = lines[lines.indexOf("your ticket:") + 1]
    validTickets.add(myTicket)

    // does not work, since 20! (factorial) too large
    //val propertyPermutations = permute(restrictions.keys.toList())

//    var actualOrder: List<String>? = null
//    for (permut in propertyPermutations) {
//        if (validTickets.all { validTicket(permut, it.split(",").map { it.toInt() }) }) {
//            actualOrder = permut
//            break
//        }
//    }

    val actualOrder = MutableList<String>(restrictions.keys.size) { "" }
    val possibleFields = List(restrictions.keys.size) { ArrayList<String>() }

    for (i in myTicket.split(",").indices) {
        for (res in restrictions.keys) {
            val test = validTickets.map { it.split(",") }.map { it.map { it.toInt() } }.map { it[i] }
            if (possibleField(res, test)) {
                possibleFields[i].add(res)
            }
        }
    }

    val sortedCandidatesWithIndex = possibleFields.withIndex().sortedBy { it.value.size }
    for (candidate in sortedCandidatesWithIndex) {
        val set = candidate.value.first()
        actualOrder[candidate.index] = set
        sortedCandidatesWithIndex.forEach { it.value.remove(set) }
    }

    var prod = 1L

    for (index in actualOrder!!.indices) {
        if (actualOrder[index].contains("departure")) {
            prod *= myTicket.split(",")[index].toLong()
        }
    }

    return prod
//    return sum
}

fun possibleField(field: String, values: List<Int>): Boolean {
    return values.all { inRange(field, it) }
}

fun inRange(field: String, value: Int): Boolean {
    return restrictions[field]!!.any { it.contains(value) }
}

fun validTicket(restrictionOrder: List<String>, ticketVals: List<Int>): Boolean {
    for (i in restrictionOrder.indices) {
        if (!restrictions[restrictionOrder[i]]!!.fold(
                false,
                { valid, range -> valid || range.contains(ticketVals[i]) })
        ) {
            return false
        }
    }
    return true
}
