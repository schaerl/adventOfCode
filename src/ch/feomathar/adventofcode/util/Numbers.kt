package ch.feomathar.adventofcode.util

fun Int.modulus(other: Int): Int {
    return (other + (this % other)) % other
}
