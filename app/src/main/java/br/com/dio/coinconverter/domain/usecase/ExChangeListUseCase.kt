package br.com.dio.coinconverter.domain.usecase

import br.com.dio.coinconverter.core.UseCase
import br.com.dio.coinconverter.data.model.ExChangeResponseValue
import br.com.dio.coinconverter.data.repository.CoinRepository
import br.com.dio.coinconverter.data.repository.CoinRepositoryImp
import kotlinx.coroutines.flow.Flow

class ExChangeListUseCase(val coinRepositoryImp: CoinRepository): UseCase.NoParam<List<ExChangeResponseValue>>() {
    override suspend fun execute(): Flow<List<ExChangeResponseValue>> {
            return coinRepositoryImp.getListExChange()
    }
}