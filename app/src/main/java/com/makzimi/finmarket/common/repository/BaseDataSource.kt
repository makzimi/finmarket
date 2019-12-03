package com.makzimi.finmarket.common.repository

import com.makzimi.finmarket.common.RepositoryResult
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

open class BaseDataSource {

    fun <T, R> getResult(   apiResponse: Single<T>,
                            emptyCreator: (Throwable) -> T,
                            converter: (T) -> RepositoryResult<R>): Single<RepositoryResult<R>> {
        return apiResponse
            .onErrorReturn(emptyCreator)
            .map(converter)
            .subscribeOn(Schedulers.io())
    }

}