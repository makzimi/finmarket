package com.makzimi.finmarket.common

class RepositoryResult<T> (val status: Status, val data: T?, val message: String?){

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): RepositoryResult<T> {
            return RepositoryResult(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String?, data: T? = null): RepositoryResult<T> {
            return RepositoryResult(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): RepositoryResult<T> {
            return RepositoryResult(Status.LOADING, data, null)
        }
    }
}