package br.com.dio.coinconverter.data.repository

import br.com.dio.coinconverter.data.model.ExChangeResponseValue
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    suspend fun getExChangeValues(coin: String): Flow<ExChangeResponseValue>

    suspend fun save(param: ExChangeResponseValue)

    fun getListExChange(): Flow<List<ExChangeResponseValue>>
}