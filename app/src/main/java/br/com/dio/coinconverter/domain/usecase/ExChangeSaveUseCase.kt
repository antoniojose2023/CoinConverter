package br.com.dio.coinconverter.domain.usecase

import br.com.dio.coinconverter.core.UseCase
import br.com.dio.coinconverter.data.model.ExChangeResponseValue
import br.com.dio.coinconverter.data.repository.CoinRepository
import br.com.dio.coinconverter.data.repository.CoinRepositoryImp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ExChangeSaveUseCase(val coinRepository: CoinRepository): UseCase.NoSource<ExChangeResponseValue>() {
    override suspend fun execute(param: ExChangeResponseValue): Flow<Unit> {
            return flow{
                coinRepository.save(param)
                emit(Unit)
            }
    }
}