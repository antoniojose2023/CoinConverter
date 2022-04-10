package br.com.dio.coinconverter.data.service

import br.com.dio.coinconverter.data.model.ExChangeResponse
import br.com.dio.coinconverter.data.model.ExChangeResponseValue
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinService {

    @GET("/json/last/{coin}")
    suspend fun coinService(@Path("coin") coin: String): ExChangeResponse

}