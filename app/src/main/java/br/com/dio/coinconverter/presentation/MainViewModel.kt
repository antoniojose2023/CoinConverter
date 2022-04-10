package br.com.dio.coinconverter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dio.coinconverter.data.model.ExChangeResponseValue
import br.com.dio.coinconverter.domain.usecase.ExChangeSaveUseCase
import br.com.dio.coinconverter.domain.usecase.ExChangeValueUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(val exChangeValueUseCase: ExChangeValueUseCase, val exChangeSaveUseCase: ExChangeSaveUseCase): ViewModel() {

    private var _state = MutableLiveData<State>()
    val state : LiveData<State> = _state

    fun getExchangeValue(param: String){
        viewModelScope.launch {
            exChangeValueUseCase(param)
                .flowOn(Dispatchers.Main)
                .onStart {
                    _state.value = State.Loading
                }
                .catch {
                    _state.value = State.Error(it)
                }
                .collect {
                    _state.value =  State.Sucess(it)
                }
        }
    }

    fun save(param: ExChangeResponseValue){
        viewModelScope.launch {
            exChangeSaveUseCase(param)
                .flowOn(Dispatchers.Main)
                .onStart {
                    _state.value = State.Loading
                }
                .catch {
                    _state.value = State.Error(it)
                }
                .collect {
                    _state.value =  State.Saving
                }
        }
    }

    sealed class State(){
        object Loading : State()
        object Saving : State()
        class Error(val error: Throwable): State()
        class Sucess(val response: ExChangeResponseValue): State()
    }

}