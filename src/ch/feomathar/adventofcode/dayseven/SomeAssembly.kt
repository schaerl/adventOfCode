package ch.feomathar.adventofcode.dayseven

import ch.feomathar.adventofcode.ProgramError
import ch.feomathar.adventofcode.util.parseInput

private val PATTERN = Regex("(.*) -> (.*)")
private val SOURCE_PATTERN = Regex("(\\w*)? ?(AND|OR|LSHIFT|RSHIFT|NOT) (.*)")
private val SIGNAL_CACHE = HashMap<String, Signal>()


fun getOutput(fileName: String): Int {
    val lines = parseInput(fileName)
    val parsed = lines.map { PATTERN.matchEntire(it)!!.groupValues }
    val map = HashMap<String, String>()
    for (list in parsed) {
        map[list[2]] = list[1]
    }

//    return getSignal(map, "a").bits.toInt()
    map["b"] = getSignal(map, "a").bits.toString()
    SIGNAL_CACHE.clear()
    return getSignal(map, "a").bits.toInt()
}

fun getSignal(map: Map<String, String>, to: String): Signal {
    if (SIGNAL_CACHE[to] != null) {
        return SIGNAL_CACHE[to]!!
    }
    val intTo = to.toIntOrNull()
    if (intTo != null) {
        SIGNAL_CACHE[to] = Signal.of(intTo)
        return SIGNAL_CACHE[to]!!
    }
    val source = map[to]
    val intSource = source?.toIntOrNull()
    if (intSource != null) {
        SIGNAL_CACHE[to] = Signal.of(intSource)
    } else {
        SIGNAL_CACHE[to] = parseSources(map, source!!)
    }
    return SIGNAL_CACHE[to]!!
}


fun parseSources(map: Map<String, String>, source: String): Signal {
    val m = SOURCE_PATTERN.matchEntire(source)
    if (m == null) {
        return getSignal(map, source)
    } else {
        val g = m.groupValues
        return when (g[2]) {
            "AND" -> getSignal(map, g[1]).and(getSignal(map, g[3]))
            "OR" -> getSignal(map, g[1]).or(getSignal(map, g[3]))
            "LSHIFT" -> getSignal(map, g[1]).lsh(g[3].toInt())
            "RSHIFT" -> getSignal(map, g[1]).rsh(g[3].toInt())
            "NOT" -> getSignal(map, g[3]).not()
            else -> throw ProgramError("Invalid operation")
        }
    }
}

class Signal(val bits: UShort) {

    fun and(other: Signal): Signal {
        return Signal(this.bits.and(other.bits))
    }

    fun or(other: Signal): Signal {
        return Signal(this.bits.or(other.bits))
    }

    fun lsh(bits: Int): Signal {
        return Signal.of(this.bits.toInt().shl(bits))
    }

    fun rsh(bits: Int): Signal {
        return Signal.of(this.bits.toInt().shr(bits))
    }

    fun not(): Signal {
        return Signal(this.bits.inv())
    }

    companion object {
        fun of(x: Int): Signal {
            return Signal(x.toUShort())
        }
    }
}
