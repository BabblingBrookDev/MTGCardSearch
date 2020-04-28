package com.babblingbrook.mtgcardsearch.data.remote

import retrofit2.Response
import java.io.IOException

sealed class ApiResponse<T> {

    companion object {
        fun <T> create(error: Throwable): ApiResponse<T> {
            error.message?.let {
                if (it.contains("Unable to resolve host") || error is IOException) {
                    return ApiNetworkError()
                }
                return ApiGenericError(
                    it
                )
            }
            return ApiGenericError("No error message")
        }

        fun <T> create(response: Response<T>): ApiResponse<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else {
                    ApiSuccessResponse(
                        body
                    )
                }
            } else {
                val errorMessage = response.errorBody()?.toString() ?: response.message()
                ApiGenericError(
                    errorMessage ?: "unknown error"
                )
            }
        }
    }
}

data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()

class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiGenericError<T>(
    val errorMessage: String
) : ApiResponse<T>()

class ApiNetworkError<T> : ApiResponse<T>()