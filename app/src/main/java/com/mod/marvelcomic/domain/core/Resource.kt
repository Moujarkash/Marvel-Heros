package com.mod.marvelcomic.domain.core

sealed class Resource<T> {
    data class Data<T>(val data: T): Resource<T>()
    data class Failure<T>(val type: FailureType, val message: String? = null): Resource<T>()
}
