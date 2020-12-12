package ch.feomathar.adventofcode.dayfour

import java.math.BigInteger
import java.security.MessageDigest
import kotlin.text.Charsets.UTF_8

private val INPUT = "bgvyzdsv"

fun crack(): Int {
    var nonce = 0
    while (true) {
        if (md5(INPUT + nonce).startsWith("000000")) {
            return nonce
        }
        nonce++
    }
}

fun md5(input: String): String {
    val md = MessageDigest.getInstance("MD5")
    val bigInt = BigInteger(1, md.digest(input.toByteArray(UTF_8)))
    return String.format("%032x", bigInt)
}
