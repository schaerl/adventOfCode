package ch.feomathar.adventofcode.dayfourteen

import ch.feomathar.adventofcode.util.parseInput
import kotlin.math.pow

private val MEM_PATTERN = Regex("mem\\[(\\d+)] = (\\d+)")

fun getMemorySum(fileName: String): Long {
    val lines = parseInput(fileName)
    var bitMask = BitArray.BitMask("")
//    val memory = HashMap<Int, BitArray>()
//    for (string in lines) {
//        if (string.startsWith("mask = ")) {
//            bitMask = BitArray.BitMask(string.removePrefix("mask = "))
//        } else {
//            val g = MEM_PATTERN.matchEntire(string)!!.groupValues
//            memory[g[1].toInt()] = BitArray(g[2].toLong()).apply(bitMask)
//        }
//    }

    // PART 2
    val memory = HashMap<BitArray, BitArray>()
    for (string in lines) {
        if (string.startsWith("mask = ")) {
            bitMask = BitArray.BitMask(string.removePrefix("mask = "))
        } else {
            val g = MEM_PATTERN.matchEntire(string)!!.groupValues
            val locs = BitArray(g[1].toLong()).applyLocation(bitMask)
            for (loc in locs) {
                memory[loc] = BitArray(g[2].toLong())
            }
        }
    }
    return memory.map { it.value.toLong() }.sum()
}

class BitArray(from: Long) {
    private val LENGTH = 36
    val bits = Array<Byte>(LENGTH) { 0 }

    init {
        var process = from
        for (i in LENGTH - 1 downTo 0) {
            bits[i] = (process % 2).toByte()
            process /= 2
        }
    }

    fun applyValue(bitMask: BitMask, loc: Boolean): BitArray {
        for (override in bitMask.overrides) {
            if (override.value != (-1).toByte()) {
                if (loc && override.value == 0.toByte()) {
                    continue    // loc mode means, that zeroes don't do anything
                }
                bits[override.key] = override.value
            }
        }
        return this
    }

    fun applyLocation(bitMask: BitMask): List<BitArray> {
        applyValue(bitMask, true)
        val overrides = bitMask.overrides.filter { it.value == (-1).toByte() }.map { it.key }
        val combinations = getCombinations(overrides, this)
        return combinations
    }

    fun toLong(): Long {
        var sum = 0L
        for (i in bits.indices) {
            sum += bits[i] * (2.0).pow((LENGTH - 1) - i).toLong()
        }
        return sum
    }

    private fun getCombinations(xPos: List<Int>, mask: BitArray): List<BitArray> {
        val mutXPos = ArrayList(xPos)
        if (xPos.isEmpty()) {
            return arrayListOf(mask)
        } else {
            val now = mutXPos.removeFirst()
            val c1 = BitArray(mask.toLong())
            val c2 = BitArray(mask.toLong())
            c1.bits[now] = 0
            c2.bits[now] = 1
            val c1List = getCombinations(mutXPos, c1)
            val c2List = getCombinations(mutXPos, c2)
            val result = ArrayList<BitArray>()
            result.addAll(c1List)
            result.addAll(c2List)
            return result
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BitArray

        if (LENGTH != other.LENGTH) return false
        if (!bits.contentEquals(other.bits)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = LENGTH
        result = 31 * result + bits.contentHashCode()
        return result
    }

    override fun toString(): String {
        return "BitArray(${toLong()})"
    }


    class BitMask(string: String) {
        val overrides = HashMap<Int, Byte>()

        init {
            for (i in string.indices) {
                if (string[i] == 'X') {
                    overrides[i] = -1
                } else {
                    overrides[i] = Character.getNumericValue(string[i]).toByte()
                }
            }
        }
    }
}
