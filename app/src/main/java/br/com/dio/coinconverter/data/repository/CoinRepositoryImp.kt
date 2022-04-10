package br.com.dio.coinconverter.data.repository


import android.util.Log
import br.com.dio.coinconverter.core.RemoteException
import br.com.dio.coinconverter.data.database.AppDatabase
import br.com.dio.coinconverter.data.model.ErroResponse
import br.com.dio.coinconverter.data.model.ExChangeResponseValue
import br.com.dio.coinconverter.data.service.CoinService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class CoinRepositoryImp(appDatabase: AppDatabase, val service: CoinService): CoinRepository {

   private val exChangeDao = appDatabase.exChangeDao()

    override suspend fun getExChangeValues(param: String) = flow {
        try{
            val exchangeResponseValue = service.coinService(param)
            val exChange = exchangeResponseValue.values.first()
            emit(exChange)
        }catch (ex: HttpException){
            var json = ex.response()?.errorBody()?.string()
            var erroResponse = Gson().fromJson(json, ErroResponse::class.java)
            throw RemoteException(erroResponse.message)
        }

    }

    override suspend fun save(param: ExChangeResponseValue) {
         exChangeDao.save( param )

    }

    override fun getListExChange(): Flow<List<ExChangeResponseValue>> {
         return exChangeDao.getExChange()
    }
}