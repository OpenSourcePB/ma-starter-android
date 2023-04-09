package az.pashabank.domain.base

import az.pashabank.domain.exceptions.ErrorConverter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlin.coroutines.CoroutineContext

abstract class BaseFlowUseCase<P, R>(
    private val executionContext: CoroutineContext,
    private val errorMapper: ErrorConverter
) {
    protected abstract fun createFlow(params: P): Flow<R>

    fun execute(params: P): Flow<R> =
        createFlow(params)
            .flowOn(executionContext)
            .catch { throw errorMapper.convert(it) }
}