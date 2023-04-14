package az.pashabank.presentation.base

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import androidx.navigation.Navigator
import az.pashabank.domain.base.BaseUseCase
import az.pashabank.domain.base.CompletionBlock
import az.pashabank.domain.exceptions.NetworkError
import az.pashabank.domain.exceptions.ServerError
import az.pashabank.presentation.tools.NavigationCommand
import az.pashabank.presentation.tools.SingleLiveEvent
import az.pashabank.starter.presentation.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

open class BaseViewModel<State, Effect> :
    ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _state = MutableLiveData<State>()

    private val _effect = SingleLiveEvent<Effect>()

    private val _commonEffect = SingleLiveEvent<BaseEffect>()

    val commonEffect: LiveData<BaseEffect>
        get() = _commonEffect

    val navigationCommands = SingleLiveEvent<NavigationCommand>()

    val state: LiveData<State>
        get() = _state

    val effect: SingleLiveEvent<Effect>
        get() = _effect

    protected fun postState(state: State) {
        _state.value = state
    }

    protected fun postEffect(effect: Effect) {
        _effect.postValue(effect)
    }

    fun navigate(directions: NavDirections, extras: Navigator.Extras? = null) {
        navigationCommands.postValue(NavigationCommand.To(directions, extras))
    }

    fun navigate(command: NavigationCommand) {
        navigationCommands.postValue(command)
    }

    private var arguments: Bundle? = null

    fun setArguments(data: Bundle?) {
        arguments = data
    }

    fun getArguments(): Bundle? {
        return arguments
    }

    /**
     * Override if arguments will be used
     */
    open fun loadArguments() {}


    suspend fun <P, R> BaseUseCase<P, R>.getWith(param: P): R {
        var result: R? = null
        val block: CompletionBlock<R> = {
            onSuccess = { result = it }
            onError = { throw it }
        }
        execute(param, block)
        return result!!
    }

    fun launchAll(
        loadingHandle: (Boolean) -> Unit = ::handleLoading,
        errorHandle: (Throwable) -> Unit = ::handleError,
        block: suspend () -> Unit
    ) {
        viewModelScope.launch {
            loadingHandle(true)
            try {
                block()
            } catch (t: Throwable) {
                errorHandle(t)
            }
            loadingHandle(false)
        }
    }

    protected fun handleError(t: Throwable) {
        Timber.e(t)
        when (t) {
            is ServerError.ServerIsDown -> _commonEffect.postValue(BackEndError())
            is ServerError.Unexpected -> _commonEffect.postValue(MessageError(R.string.error_unexpected))
            is NetworkError -> _commonEffect.postValue(NoInternet())
            else -> _commonEffect.postValue(UnknownError(cause = t))
        }
    }

    protected fun <T> Flow<T>.handleLoading(loadingHandle: (Boolean) -> Unit = ::handleLoading): Flow<T> =
        flow {
            this@handleLoading
                .onStart { loadingHandle(true) }
                .onCompletion { loadingHandle(false) }
                .collect { value ->
                    loadingHandle(false)
                    emit(value)
                }
        }

    protected fun <T> Flow<T>.handleError(): Flow<T> = catch { handleError(it) }

    protected fun <T> Flow<T>.launch(
        scope: CoroutineScope = viewModelScope,
        loadingHandle: (Boolean) -> Unit = ::handleLoading
    ): Job =
        this.handleError()
            .handleLoading(loadingHandle)
            .launchIn(scope)

    protected fun <T> Flow<T>.launchNoLoading(scope: CoroutineScope = viewModelScope): Job =
        this.handleError()
            .launchIn(scope)

    protected fun <P, R, U : BaseUseCase<P, R>> U.launch(
        param: P,
        loadingHandle: (Boolean) -> Unit = ::handleLoading,
        block: CompletionBlock<R> = {}
    ) {
        viewModelScope.launch {
            val actualRequest = BaseUseCase.Request<R>().apply(block)

            val proxy: CompletionBlock<R> = {
                onStart = {
                    loadingHandle(true)
                    actualRequest.onStart?.invoke()
                }
                onSuccess = {
                    actualRequest.onSuccess(it)
                }
                onCancel = {
                    actualRequest.onCancel?.invoke(it)
                }
                onTerminate = {
                    loadingHandle(false)
                    actualRequest.onTerminate
                }
                onError = {
                    Timber.e(it)
                    actualRequest.onError?.invoke(it) ?: handleError(it)
                }
            }
            execute(param, proxy)
        }
    }

    protected fun <P, R, U : BaseUseCase<P, R>> U.launchNoLoading(
        param: P,
        block: CompletionBlock<R> = {}
    ) {
        viewModelScope.launch {
            val actualRequest = BaseUseCase.Request<R>().apply(block)


            val proxy: CompletionBlock<R> = {
                onStart = actualRequest.onStart
                onSuccess = actualRequest.onSuccess
                onCancel = actualRequest.onCancel
                onTerminate = actualRequest.onTerminate
                onError = {
                    Timber.e(it)
                    actualRequest.onError?.invoke(it) ?: handleError(it)
                }
            }
            execute(param, proxy)
        }
    }

    protected fun <P, R, U : BaseUseCase<P, R>> U.launchUnsafe(
        param: P,
        block: CompletionBlock<R> = {}
    ) {
        viewModelScope.launch { execute(param, block) }
    }

    protected fun handleLoading(state: Boolean) {
        _isLoading.postValue(state)
    }
}