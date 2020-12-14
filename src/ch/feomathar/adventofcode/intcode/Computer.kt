package ch.feomathar.adventofcode.intcode

import ch.feomathar.adventofcode.ProgramError

class Computer(inMemory: List<Int>) {

    private val memory = ArrayList(inMemory)
    var pc = 0

    val stdin = ArrayDeque<String>()
    val stdout = ArrayDeque<String>()

    fun setMemory(input: List<Int>) {
        memory.clear()
        memory.addAll(input)
    }

    fun setByte(pos: Int, value: Int) {
        memory[pos] = value
    }

    fun getMemory(): List<Int> {
        return ArrayList(memory)
    }

    fun getByte(pos: Int): Int {
        return memory[pos]
    }

    fun resetPc() {
        pc = 0
    }

    fun runProgram() {
        do {
            val operation = getOp()
            operation.execute(this)
        } while (operation != Exit)
    }

    fun addInput(line: String) {
        stdin.addLast(line)
    }

    private fun getOp(): Operation {
        val instructionDescription = memory[pc]
        val insString = instructionDescription.toString()
        val opCode: Int
        val paramTypeStack = ArrayDeque<Int>()
        if (insString.length <= 2) {
            opCode = instructionDescription
        } else {
            opCode = insString.substring(insString.length - 2).toInt()
            for (i in insString.substring(0, insString.length - 2).indices.reversed()) {
                paramTypeStack.add(insString[i].toString().toInt())
            }
        }

        return when (opCode) {
            1 -> Add(Parameter(memory[pc + 1], paramTypeStack.removeFirstOrNull() ?: 0),
                    Parameter(memory[pc + 2], paramTypeStack.removeFirstOrNull() ?: 0),
                    memory[pc + 3])
            2 -> Mul(Parameter(memory[pc + 1], paramTypeStack.removeFirstOrNull() ?: 0),
                    Parameter(memory[pc + 2], paramTypeStack.removeFirstOrNull() ?: 0),
                    memory[pc + 3])
            3 -> In(memory[pc + 1])
            4 -> Out(Parameter(memory[pc + 1], paramTypeStack.removeFirstOrNull() ?: 0))
            5 -> JIT(Parameter(memory[pc + 1], paramTypeStack.removeFirstOrNull() ?: 0),
                    Parameter(memory[pc + 2], paramTypeStack.removeFirstOrNull() ?: 0))
            6 -> JIF(Parameter(memory[pc + 1], paramTypeStack.removeFirstOrNull() ?: 0),
                    Parameter(memory[pc + 2], paramTypeStack.removeFirstOrNull() ?: 0))
            7 -> LT(Parameter(memory[pc + 1], paramTypeStack.removeFirstOrNull() ?: 0),
                    Parameter(memory[pc + 2], paramTypeStack.removeFirstOrNull() ?: 0),
                    memory[pc + 3])
            8 -> EQ(Parameter(memory[pc + 1], paramTypeStack.removeFirstOrNull() ?: 0),
                    Parameter(memory[pc + 2], paramTypeStack.removeFirstOrNull() ?: 0),
                    memory[pc + 3])

            99 -> Exit
            else -> throw ProgramError("Invalid Opcode")
        }
    }
}

sealed class Operation(private val pcJump: Int) {
    open fun execute(computer: Computer) {
        doExecute(computer)
        incPc(computer)
    }

    abstract fun doExecute(computer: Computer)

    protected open fun incPc(computer: Computer) {
        computer.pc += pcJump
    }
}

data class Add(val orig1: Parameter, val orig2: Parameter, val dest: Int) : Operation(4) {
    override fun doExecute(computer: Computer) {
        computer.setByte(dest, orig1.getValue(computer) + orig2.getValue(computer))
    }
}

data class Mul(val orig1: Parameter, val orig2: Parameter, val dest: Int) : Operation(4) {
    override fun doExecute(computer: Computer) {
        computer.setByte(dest, orig1.getValue(computer) * orig2.getValue(computer))
    }
}

object Exit : Operation(0) {
    override fun doExecute(computer: Computer) {
        // NOOP
    }
}

data class In(val pos: Int) : Operation(2) {
    override fun doExecute(computer: Computer) {
        computer.setByte(pos, computer.stdin.removeFirst().toInt())
    }

}

data class Out(val pos: Parameter) : Operation(2) {
    override fun doExecute(computer: Computer) {
        computer.stdout.addLast(pos.getValue(computer).toString())
    }
}

data class JIT(val condition: Parameter, val dest: Parameter) : Operation(3) {
    override fun doExecute(computer: Computer) {
        // NOOP, only affects PC
    }

    override fun incPc(computer: Computer) {
        if (condition.getValue(computer) != 0) {
            computer.pc = dest.getValue(computer)
        } else {
            super.incPc(computer)
        }
    }

}

data class JIF(val condition: Parameter, val dest: Parameter) : Operation(3) {
    override fun doExecute(computer: Computer) {
        // NOOP, only affects PC
    }

    override fun incPc(computer: Computer) {
        if (condition.getValue(computer) == 0) {
            computer.pc = dest.getValue(computer)
        } else {
            super.incPc(computer)
        }
    }
}

data class LT(val a: Parameter, val b: Parameter, val dest: Int) : Operation(4) {
    override fun doExecute(computer: Computer) {
        computer.setByte(dest, if (a.getValue(computer) < b.getValue(computer)) 1 else 0)
    }
}

data class EQ(val a: Parameter, val b: Parameter, val dest: Int) : Operation(4) {
    override fun doExecute(computer: Computer) {
        computer.setByte(dest, if (a.getValue(computer) == b.getValue(computer)) 1 else 0)
    }
}

data class Parameter(val value: Int, val mode: Int) {
    fun getValue(computer: Computer): Int {
        return if (mode == 0) {
            computer.getByte(value)
        } else {
            value
        }
    }
}
