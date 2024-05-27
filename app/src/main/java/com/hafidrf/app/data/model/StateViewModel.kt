package com.hafidrf.app.data.model

sealed class LoaderState {
    data class OnLoading(val isLoading: Boolean): LoaderState()
}

sealed class ResultState<out T> {
    data class Success<out T>(
        val data: T?,
        val requestCode: Int = 0,
        val responseCode: Int? = null,
        val extra: Any? = null
    ) : ResultState<T>()
    data class Error(
        val requestCode: Int = 0,
        val errorCode: Int? = null,
        val exception: Throwable? = null,
        val data: Any? = null,
        val extra: Any? = null
    ) : ResultState<Nothing>()
}