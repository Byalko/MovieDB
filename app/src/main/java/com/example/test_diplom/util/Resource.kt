package com.example.test_diplom.util

import java.lang.Exception

sealed class Resource<T>(val data: T?=null, val message: String?=null,val e: Exception?=null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String) : Resource<T>(null, message)
    class ErrorException<T>(e: Exception) : Resource<T>(null, null,e)
    class Loading<T> :Resource<T>()
}