package com.dedechandran.core.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

abstract class NetworkBoundResource<ResultType, RequestType> {

    private val result: Flow<ResultType> = flow {
        val dbSource = loadFromDb().first()
        if(shouldFetch(dbSource)){
            val apiResponse = createCall()
            saveCallResult(apiResponse)
            emitAll(loadFromDb())
        }else{
            emitAll(loadFromDb())
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDb(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): RequestType

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<ResultType> = result
}