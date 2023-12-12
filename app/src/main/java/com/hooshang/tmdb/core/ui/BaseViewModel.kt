package com.hooshang.tmdb.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hooshang.tmdb.core.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

interface ViewAction
interface ViewState
abstract class BaseViewModel<A : ViewAction, S : ViewState> : ViewModel() {
    open fun onAction(action: A) = Unit
    abstract fun setInitialState(): S

    private val _state = MutableStateFlow(this.setInitialState())
    val state = _state.asStateFlow()

    protected fun setState(reducer: S.() -> S) {
        viewModelScope.launch {
            _state.emit(_state.value.reducer())
        }
    }

    protected suspend fun <T> resultWrapper(
        action: suspend () -> T
    ): Flow<Result> {
        return flow {
            emit(Result.Loading)
            try {
                emit(Result.Success(action.invoke()))
            } catch (t: Throwable) {
                emit(Result.Error(t.message ?: "unexpected error"))
            }
        }
    }
}