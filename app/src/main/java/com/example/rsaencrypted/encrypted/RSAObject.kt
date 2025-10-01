package com.example.rsaencrypted.encrypted

import java.math.BigInteger
import java.security.SecureRandom

class RSAObject {
    val bitLength = 1024
    val rnd = SecureRandom()

    // p/q простые числа длинной 1024 бит
    var p = BigInteger.probablePrime(bitLength, rnd)
    var q = BigInteger.probablePrime(bitLength, rnd)

    val e = 65537 // Открытая (публичная) экспонента
    var n = p * q   // Произведение p и q
    val phi = (p - BigInteger.ONE) * (q - BigInteger.ONE)
    val d = BigInteger.valueOf(e.toLong()).modInverse(phi)
}