package br.com.dio.coinconverter.domain.di

import br.com.dio.coinconverter.domain.usecase.ExChangeListUseCase
import br.com.dio.coinconverter.domain.usecase.ExChangeSaveUseCase
import br.com.dio.coinconverter.domain.usecase.ExChangeValueUseCase
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModule {

    fun load(){
        loadKoinModules(useCaseValue())
    }

    private fun useCaseValue(): Module {
            return module {
                 factory { ExChangeValueUseCase(get()) }
                 factory { ExChangeSaveUseCase(get()) }
                 factory { ExChangeListUseCase(get()) }

            }
    }
}