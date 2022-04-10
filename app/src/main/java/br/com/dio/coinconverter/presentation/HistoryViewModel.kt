package br.com.dio.coinconverter.presentation

import androidx.lifecycle.*
import br.com.dio.coinconverter.data.model.ExChangeResponseValue
import br.com.dio.coinconverter.domain.usecase.ExChangeListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HistoryViewModel(val exChangeListUseCase: ExChangeListUseCase): ViewModel(), LifecycleObserver {

    private val state = MutableLiveData<State>()
    var _state : LiveData<State> = state


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun listExChangeResponseValue(){
            viewModelScope.launch {
                exChangeListUseCase()
                    .flowOn(Dispatchers.Main)
                    .onStart {
                         state.value = State.Loading
                    }
                    .catch {
                        state.value = State.Error(it)
                    }
                    .collect{
                        state.value = State.Sucess(it)
                    }

            }
    }

    sealed class State(){
        object Loading: State()
        data class Error(val erro: Throwable): State()
        data class Sucess(val list: List<ExChangeResponseValue>): State()
    }

}