package com.example.gymquizapp

sealed class FunctionResponse <T> (var success: Boolean, val error: String?, val responseData: T? = null) {

    class Error <T> (error: String): FunctionResponse<T>(false, error, null)

    class Success <T> (responseData: T): FunctionResponse<T> (true, null, responseData)
}