package ch.feomathar.adventofcode.dayfifteen

private val INPUT = intArrayOf(0, 3, 1, 6, 7, 5)

fun memory(): Int {
    val memory = HashMap<Int, ArrayList<Int>>()
    var i = 0
    var lastNum = 0
    while (i < 30000000) {
        lastNum = if (i < INPUT.size) {
            INPUT[i]
        } else {
            // If two in memory, get difference, else i, so that difference is 0
            if (memory[lastNum] != null && memory[lastNum]!!.size >= 2) {
                memory[lastNum]!!.last() - memory[lastNum]!![memory[lastNum]!!.size - 2]
            } else {
                0
            }
        }
        if (memory[lastNum] == null) {
            memory[lastNum] = ArrayList()
        }
        memory[lastNum]!!.add(i)
        i++
    }
    return lastNum
}
