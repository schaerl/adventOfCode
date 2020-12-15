package ch.feomathar.adventofcode.dayseven

import ch.feomathar.adventofcode.util.parseInput

fun getIPs(fileName: String): Int {
    var ips = parseInput(fileName)
    return ips.filter { validABA(it) }.count()
}

private fun validAbba(ip: String): Boolean {
    var prePrePrev = '0'
    var prePrev = '0'
    var prev = '0'
    var current = '0'
    var inBracks = false
    var hasFlip = false
    for (i in ip) {
        prePrePrev = prePrev
        prePrev = prev
        prev = current
        current = i
        if (current == '[') {
            inBracks = true
            continue
        }
        if (current == ']') {
            inBracks = false
        }
        if (prePrePrev == current && prePrev == prev && current != prev) {
            if (inBracks) {
                return false
            } else {
                hasFlip = true
            }
        }

    }

    return hasFlip
}

private fun validABA(ip: String): Boolean {
    var prePrev = '0'
    var prev = '0'
    var current = '0'
    var inBracks = false
    var hasFlip = false
    val inBracksTriples = HashSet<String>()
    val outBracksTriples = HashSet<String>()
    for (i in ip) {
        prePrev = prev
        prev = current
        current = i
        if (current == '[') {
            inBracks = true
            continue
        }
        if (current == ']') {
            inBracks = false
        }
        if (prePrev == current && current != prev) {
            if (inBracks) {
                inBracksTriples.add(prePrev.toString() + prev.toString() + current.toString())
            } else {
                outBracksTriples.add(prePrev.toString() + prev.toString() + current.toString())
            }
        }

    }

    for (trip in inBracksTriples) {
        if (outBracksTriples.contains(trip[1].toString() + trip[0].toString() + trip[1].toString())) {
            return true
        }
    }

    return false
}
