package com.example.rsaencrypted.encrypted

import java.math.BigInteger

fun decryptMessage(
    message: MutableList<BigInteger>,
    d: BigInteger,
    n: BigInteger,
    viewModel: EncryptedLogsViewModel
): String {
    val string = StringBuilder()

    viewModel.log("\n\n\nДЕШИФРОВКА...")
    message.forEach { it ->
        val symbol = it.modPow(d, n)
        viewModel.log("code = $it -> code^d mod n = ${symbol.toInt().toChar()} ( где n произведение p и q )\n")
        string.append(symbol.toInt().toChar())
    }

    viewModel.log("ОКОНЕЧНЫЙ ТЕКСТ: $string")
    return string.toString()
}