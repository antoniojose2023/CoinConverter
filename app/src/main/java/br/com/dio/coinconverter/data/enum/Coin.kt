package br.com.dio.coinconverter.data.enum

import br.com.dio.coinconverter.core.extensions.formatCurrency
import br.com.dio.coinconverter.data.model.ExChangeResponseValue
import java.util.*

enum class Coin(val locale: Locale) {
    USD(Locale.US),
    CAD(Locale.CANADA),
    BRL(Locale("pt", "BR")),
    ARS(Locale("es", "AR"));


    companion object{
        fun getCurrency(name: String) = values().find { it.name == name } ?: BRL
    }

}