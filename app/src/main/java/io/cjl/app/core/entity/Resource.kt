package io.cjl.app.core.entity

import io.cjl.app.core.entity.Resource.Companion.success

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val code: Int = 0
) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        fun <T> error(code: Int = 0, msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg, code)
        }

        fun <T> none(data: T?): Resource<T> {
            return Resource(Status.NONE, null, null)
        }
    }

    fun copyNothing(): Resource<Nothing> {
        return Resource(status, null, message, code)
    }
}

inline fun <T, R> Resource<T>.flatMap(transform: (T) -> R): Resource<R> {
    if (status == Status.SUCCESS && data != null) {
        return success(transform(data))
    }
    return this.copyNothing()
}
enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
    NONE
}