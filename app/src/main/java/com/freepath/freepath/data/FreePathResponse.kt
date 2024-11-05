package com.freepath.freepath.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FreePathResponse<T>(
    @SerialName("data")
    val data: T? = null,
    @SerialName("error")
    val error: ErrorResponse? = null,
    @SerialName("result")
    val result: String,
) {
    @Serializable
    data class ErrorResponse(
        @SerialName("code")
        val code: String,
        @SerialName("data")
        val data: String,
        @SerialName("message")
        val message: String,
    )
}