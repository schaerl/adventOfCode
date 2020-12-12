package ch.feomathar.adventofcode.dayone

import ch.feomathar.adventofcode.util.parseInput

fun captcha(fileName:String): Int{
    val oneLine = parseInput(fileName)
    val numberList = oneLine[0].toCharArray().map { it.toString().toInt() }
    var sum = 0
    for(i in 0 until numberList.size){
//        if (numberList[i] == numberList[(i+1)%(numberList.size)]){
        if (numberList[i] == numberList[(i+numberList.size/2)%(numberList.size)]){
            sum += numberList[i]
        }
    }
    return sum
}
