package ch.feomathar.adventofcode.dayseven

import ch.feomathar.adventofcode.util.parseInput

private val PATTERN = Regex("Step (\\w) must be finished before step (\\w) can begin.")

fun getOrder(fileName: String): String {
    val lines = parseInput(fileName)
    val orders = lines.map { PATTERN.matchEntire(it)!!.groupValues }

    val tree = HashMap<String, MutableList<String>>()
    val incomingEdges = HashMap<String, MutableSet<String>>()
    for (order in orders) {
        tree[order[1]] = tree.getOrDefault(order[1], ArrayList())
        tree[order[1]]!!.add(order[2])
        incomingEdges[order[2]] = incomingEdges.getOrDefault(order[2], HashSet())
        incomingEdges[order[2]]!!.add(order[1])
    }

    val roots = ch.feomathar.adventofcode.util.findRoots(tree)
    var firstRoot = roots.min()!!
    var result = ""

    val queue = ArrayList<String>()
    val all = HashSet(tree.keys)
    all.addAll(tree.values.flatten())
    queue.addAll(all)

//    while (queue.isNotEmpty()) {
//        val processed = queue.sortedWith(compareBy({ incomingEdges[it]?.size ?: 0 }, { it })).first()
//        queue.remove(processed)
//        for (edges in incomingEdges) {
//            // processed node is no longer blocking others
//            edges.value.remove(processed)
//        }
//        // If tree[processed]== null -> last element of DAG, just add it
//        result += processed
//    }
    val workers = Array(5) { Pair("", 0) }
    var time = 0
    while (queue.isNotEmpty() || workers.any { it.first.isNotBlank() }) {
        // first do work
        for (i in workers.indices) {
            val workerPair = workers[i]
            if (workerPair.first.isNotBlank()) {
                // worker is assigned
                workers[i] = Pair(workerPair.first, workerPair.second - 1)
                if (workers[i].second == 0) {
                    result += workerPair.first
                    workers[i] = Pair("", 0)
                    // it is done, remove it
                    for (edges in incomingEdges) {
                        // processed node is no longer blocking others
                        edges.value.remove(workerPair.first)
                    }
                }

            }
        }
        // now assign new
        for (i in workers.indices) {
            val workerPair = workers[i]
            if (workerPair.first.isBlank() && queue.isNotEmpty()) {
                // if worker is free, and next process incoming size == 0, assign worker
                val processed = queue.sortedWith(compareBy({ incomingEdges[it]?.size ?: 0 }, { it })).first()
                if (incomingEdges[processed].isNullOrEmpty()) {
                    queue.remove(processed)
                    // -'A' + 1 -> get amount of steps
                    workers[i] = Pair(processed, 60 + (processed[0] - 'A' + 1))
                }
            }
        }
        time++
    }

    // have to offset back down by one, because we overcount in the end
    time--
    return time.toString()
}
