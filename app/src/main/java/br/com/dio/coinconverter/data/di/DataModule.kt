package br.com.dio.coinconverter.data.di

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import br.com.dio.coinconverter.data.database.AppDatabase
import br.com.dio.coinconverter.data.repository.CoinRepository
import br.com.dio.coinconverter.data.repository.CoinRepositoryImp
import br.com.dio.coinconverter.data.service.CoinService

import com.google.gson.GsonBuilder

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

import retrofit2.converter.gson.GsonConverterFactory

object DataModule {

    fun load(){
       loadKoinModules(networkModule() + repositoryModule() + dataBaseModule())
    }

    private fun networkModule(): Module{
        val HTTPLOGGININTERCEPOR = "ok_http"

            return module {
                single {
                    var interceptor = HttpLoggingInterceptor{
                        Log.i(HTTPLOGGININTERCEPOR, it)
                    }

                    interceptor.level = HttpLoggingInterceptor.Level.BODY

                    OkHttpClient.Builder()
                        .addInterceptor(interceptor)
                        .build()
                }

                single {
                    GsonConverterFactory.create(GsonBuilder().create())
                }

                single {
                    createService(get(), get())
                }

            }


    }

    private fun repositoryModule(): Module{
        return module {
            single<CoinRepository> { CoinRepositoryImp(get(), get()) }
        }

    }

    private fun dataBaseModule(): Module{
        return module {
            single { AppDatabase.getInstance(androidContext()) }
        }
    }

    private fun createService(client: OkHttpClient, factory: GsonConverterFactory): CoinService {
        return Retrofit.Builder()
            .baseUrl("https://economia.awesomeapi.com.br")
            .client(client)
            .addConverterFactory(factory)
            .build()
            .create(CoinService::class.java)
    }

}

