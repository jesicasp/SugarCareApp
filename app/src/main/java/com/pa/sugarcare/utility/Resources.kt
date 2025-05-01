package com.pa.sugarcare.utility

sealed class Resources<out R> private constructor() {
    data class Success<out T>(val data: T) : Resources<T>()

    //    data class Error<out T>(val error: T) : Resources<T>()
    data class Error(val error: String) : Resources<Nothing>()

    object Loading : Resources<Nothing>()
}