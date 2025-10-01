package com.example.rsaencrypted.encrypted

import java.math.BigInteger

fun encryptedMessage(message:String, e: Int, n: BigInteger, viewModel: EncryptedLogsViewModel): MutableList<BigInteger> {
    val encryptedCharArray = mutableListOf<BigInteger>()
    message.forEach { it ->
        val codeSymbol = it.code.toBigInteger()
        val encryptedMessage = codeSymbol.pow(e) % n
        viewModel.log("$it = ${it.code}^$e mod n ( где n произведение p и q )")
        encryptedCharArray.add(encryptedMessage)
    }

    viewModel.log("ОТПРАВЛЕННОЕ СООБЩЕНИЕ: $message\n")

    viewModel.log("ОРИГИНАЛЬНЫЙ ТЕКСТ $message\nЗАШИФРОВАННЫЙ ТЕКСТ: ${encryptedCharArray} ")

    return encryptedCharArray
}