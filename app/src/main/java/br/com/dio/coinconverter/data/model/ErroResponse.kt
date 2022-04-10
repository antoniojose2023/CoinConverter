package br.com.dio.coinconverter.data.model

data class ErroResponse(
    var status: String,
    var code: String,
    var message: String
) {

}