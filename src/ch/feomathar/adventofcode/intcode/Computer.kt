package ch.feomathar.adventofcode.intcode

import ch.feomathar.adventofcode.ProgramError

class Computer(inMemory: List<Long>) {

    private val memory = ArrayList(inMemory)
    var pc = 0
    var relBase = 0

    val stdin = ArrayDeque<String>()
    val stdout = ArrayDeque<String>()

    fun setMemory(input: List<Long>) {
        memory.clear()
        memory.addAll(input)
    }

    fun setByte(pos: Int, value: Long) {
        resizeMemoryIfNecessary(pos)
        memory[pos] = value
    }

    fun getMemory(): List<Long> {
        return ArrayList(memory)
    }

    fun getByte(pos: Int): Long {
        resizeMemoryIfNecessary(pos)
        return memory[pos]
    }

    fun resizeMemoryIfNecessary(pos: Int) {
        // TODO : Refactor into memory class
        if (pos < 0) {
            throw ProgramError("Negative Memory Accessed")
        } else if (pos >= memory.size) {
            // resize to arbitrary size
            memory.addAll(Array(pos - memory.size + 1) { 0 })
        }
    }

    fun resetPc() {
        pc = 0
    }

    fun runProgram(): Operation {
        var operation: Operation
        do {
            operation = getOp()
            val canContinue = operation.execute(this)
        } while (canContinue)
        return operation
    }

    fun addInput(line: String) {
        stdin.addLast(line)
    }

    private fun getOp(): Operation {
        val instructionDescription = memory[pc]
        val insString = instructionDescription.toString()
        val opCode: Long
        val paramTypeStack = ArrayDeque<Int>()
        if (insString.length <= 2) {
            opCode = instructionDescription
        } else {
            opCode = insString.substring(insString.length - 2).toLong()
            for (i in insString.substring(0, insString.length - 2).indices.reversed()) {
                paramTypeStack.add(insString[i].toString().toInt())
            }
        }

        return when (opCode.toInt()) {
            1 -> Add(
                Parameter(memory[pc + 1], paramTypeStack.removeFirstOrNull() ?: 0),
                Parameter(memory[pc + 2], paramTypeStack.removeFirstOrNull() ?: 0),
                memory[pc + 3].toInt()
            )
            2 -> Mul(
                Parameter(memory[pc + 1], paramTypeStack.removeFirstOrNull() ?: 0),
                Parameter(memory[pc + 2], paramTypeStack.removeFirstOrNull() ?: 0),
                memory[pc + 3].toInt()
            )
            3 -> In(memory[pc + 1].toInt())
            4 -> Out(Parameter(memory[pc + 1], paramTypeStack.removeFirstOrNull() ?: 0))
            5 -> JIT(
                Parameter(memory[pc + 1], paramTypeStack.removeFirstOrNull() ?: 0),
                Parameter(memory[pc + 2], paramTypeStack.removeFirstOrNull() ?: 0)
            )
            6 -> JIF(
                Parameter(memory[pc + 1], paramTypeStack.removeFirstOrNull() ?: 0),
                Parameter(memory[pc + 2], paramTypeStack.removeFirstOrNull() ?: 0)
            )
            7 -> LT(
                Parameter(memory[pc + 1], paramTypeStack.removeFirstOrNull() ?: 0),
                Parameter(memory[pc + 2], paramTypeStack.removeFirstOrNull() ?: 0),
                memory[pc + 3].toInt()
            )
            8 -> EQ(
                Parameter(memory[pc + 1], paramTypeStack.removeFirstOrNull() ?: 0),
                Parameter(memory[pc + 2], paramTypeStack.removeFirstOrNull() ?: 0),
                memory[pc + 3].toInt()
            )
            9 -> RBSHIFT(
                Parameter(memory[pc + 1], paramTypeStack.removeFirstOrNull() ?: 0)
            )
            99 -> Exit
            else -> throw ProgramError("Invalid Opcode")
        }
    }
}

sealed class Operation(private val pcJump: Int) {
    open fun execute(computer: Computer): Boolean {
        return if (doExecute(computer)) {
            incPc(computer)
            true
        } else {
            false
        }
    }

    abstract fun doExecute(computer: Computer): Boolean

    protected open fun incPc(computer: Computer) {
        computer.pc += pcJump
    }
}

data class Add(val orig1: Parameter, val orig2: Parameter, val dest: Int) : Operation(4) {
    override fun doExecute(computer: Computer): Boolean {
        computer.setByte(dest, orig1.getValue(computer) + orig2.getValue(computer))
        return true
    }
}

data class Mul(val orig1: Parameter, val orig2: Parameter, val dest: Int) : Operation(4) {
    override fun doExecute(computer: Computer): Boolean {
        computer.setByte(dest, orig1.getValue(computer) * orig2.getValue(computer))
        return true
    }
}

object Exit : Operation(0) {
    override fun doExecute(computer: Computer): Boolean {
        return false
        // NOOP
    }
}

data class In(val pos: Int) : Operation(2) {
    override fun doExecute(computer: Computer): Boolean {
        if (computer.stdin.size < 1) {
            return false
        }
        computer.setByte(pos, computer.stdin.removeFirst().toLong())
        return true
    }

}

data class Out(val pos: Parameter) : Operation(2) {
    override fun doExecute(computer: Computer): Boolean {
        computer.stdout.addLast(pos.getValue(computer).toString())
        return true
    }
}

data class JIT(val condition: Parameter, val dest: Parameter) : Operation(3) {
    override fun doExecute(computer: Computer): Boolean {
        // NOOP, only affects PC
        return true
    }

    override fun incPc(computer: Computer) {
        if (condition.getValue(computer) != 0L) {
            computer.pc = dest.getValue(computer).toInt()
        } else {
            super.incPc(computer)
        }
    }

}

data class JIF(val condition: Parameter, val dest: Parameter) : Operation(3) {
    override fun doExecute(computer: Computer): Boolean {
        // NOOP, only affects PC
        return true
    }

    override fun incPc(computer: Computer) {
        if (condition.getValue(computer) == 0L) {
            computer.pc = dest.getValue(computer).toInt()
        } else {
            super.incPc(computer)
        }
    }
}

data class LT(val a: Parameter, val b: Parameter, val dest: Int) : Operation(4) {
    override fun doExecute(computer: Computer): Boolean {
        computer.setByte(dest, if (a.getValue(computer) < b.getValue(computer)) 1 else 0)
        return true
    }
}

data class EQ(val a: Parameter, val b: Parameter, val dest: Int) : Operation(4) {
    override fun doExecute(computer: Computer): Boolean {
        computer.setByte(dest, if (a.getValue(computer) == b.getValue(computer)) 1 else 0)
        return true
    }
}

data class RBSHIFT(val offset: Parameter) : Operation(2) {
    override fun doExecute(computer: Computer): Boolean {
        computer.relBase += offset.getValue(computer).toInt()
        return true
    }
}

data class Parameter(val value: Long, val mode: Int) {
    fun getValue(computer: Computer): Long {
        return when (mode) {
            0 -> computer.getByte(value.toInt())
            2 -> computer.getByte((computer.relBase + value).toInt())
            else -> value
        }
    }

}

enum class ParameterMode {
    POS, IMM, REL
}
