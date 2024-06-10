package com.example.gymquizapp

sealed class FunctionResponse (var success: Boolean, error: String?, responseData: Any? = null) {

    class Error (val error: String): FunctionResponse(false, error, null)

    class Success (responseData: Any): FunctionResponse (true, null, responseData)
}