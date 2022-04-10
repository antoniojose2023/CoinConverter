package br.com.dio.coinconverter.domain.usecase

import br.com.dio.coinconverter.core.UseCase
import br.com.dio.coinconverter.data.model.ExChangeResponseValue
import br.com.dio.coinconverter.data.repository.CoinRepository
import kotlinx.coroutines.flow.Flow

class ExChangeValueUseCase(val coinRepository: CoinRepository): UseCase<String, ExChangeResponseValue>() {
    override suspend fun execute(param: String): Flow<ExChangeResponseValue> {
         return coinRepository.getExChangeValues(param)
    }

}