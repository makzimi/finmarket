package com.makzimi.finmarket.network.stocks

open class StocksApiResponse(
    val error: Boolean = false,
    val message: String? = null
) {
    open fun getData() {}

    companion object {
        fun generateError(): StocksApiResponse {
            return StocksApiResponse(true, "Internal error")
        }

        fun generateError(throwable: Throwable): StocksApiResponse {
            return StocksApiResponse(true, throwable.message ?: "Internal error")
        }
    }
}