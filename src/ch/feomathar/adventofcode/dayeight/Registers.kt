package ch.feomathar.adventofcode.dayeight

import ch.feomathar.adventofcode.ProgramError
import ch.feomathar.adventofcode.util.parseInput
import kotlin.math.max

private val PATTERN = Regex("(\\w+) (inc|dec) (-?\\w+) if (\\w+) (<|>|<=|>=|==|!=) (-?\\w+)")

fun largestRegister(fileName: String): Int {
    val lines = parseInput(fileName)

    val registers = HashMap<String, Int>()

    var maxContent = 0
    for (instruction in lines) {
        val setVal = applyInstruction(instruction, registers)
        maxContent = max(maxContent, setVal)
    }

    return maxContent
}

fun applyInstruction(instruction: String, registers: HashMap<String, Int>): Int {
    val g = PATTERN.matchEntire(instruction)!!.groupValues
    val target = g[1]
    val op = g[2]
    val amount = g[3].toInt()
    val conditionReg = g[4]
    val condOp = g[5]
    val condComparator = g[6].toInt()

    if (Op.parse(condOp).execute(registers[conditionReg] ?: 0, condComparator)) {
        var newVal = registers[target] ?: 0
        if (op == "inc") {
            newVal += amount
        } else {
            newVal -= amount
        }
        registers[target] = newVal
        return newVal
    }
    return -1
}

sealed class Op {

    abstract fun execute(a: Int, b: Int): Boolean

    companion object {
        fun parse(string: String): Op {
            return when (string) {
                "<" -> LT
                "<=" -> LTE
                ">" -> GT
                ">=" -> GTE
                "==" -> EQ
                "!=" -> NEQ
                else -> throw ProgramError("Invalid op")
            }
        }
    }
}

object LT : Op() {
    override fun execute(a: Int, b: Int): Boolean {
        return a < b
    }
}

object LTE : Op() {
    override fun execute(a: Int, b: Int): Boolean {
        return a <= b
    }
}

object GT : Op() {
    override fun execute(a: Int, b: Int): Boolean {
        return a > b
    }
}

object GTE : Op() {
    override fun execute(a: Int, b: Int): Boolean {
        return a >= b
    }
}

object EQ : Op() {
    override fun execute(a: Int, b: Int): Boolean {
        return a == b
    }
}

object NEQ : Op() {
    override fun execute(a: Int, b: Int): Boolean {
        return a != b
    }
}
