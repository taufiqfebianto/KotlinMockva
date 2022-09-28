package com.example.mockvakotlin.retrofit


import model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiEndpoint {

    @POST("rest/auth/login")
    fun login(@Body content: LoginRequest): Call<LoginResponse>

    @GET("rest/account/detail")
    fun getAccountDetail(
        @Header("_sessionId") _sessionId: String?,
        @Query("id") id: String?
    ): Call<GetAccountDetailResponse>

    @DELETE("rest/auth/logout")
    fun logout(@Header("_sessionId") _sessionId: String?): Call<Unit>

    @POST("rest/account/transaction/transferInquiry")
    fun transferInquiry(
        @Body content: TransferInquiryRequest,
        @Header("_sessionId") _sessionId: String?
    ): Call<TransferInquiryResponse>

    @POST("rest/account/transaction/transfer")
    fun transferConfirmation(
        @Body content: TransferConfirmationRequest, @Header("_sessionId") _sessionId: String?
    ): Call<TransferConfirmationResponse>

    @GET("rest/account/transaction/log/fetch")
    fun logTransaction(
        @Header("_sessionId") _sessionId: String?,
        @Query("accountSrcId") accountSrcId: String?
    ): Call<List<LogTransactionResponse>>


//    @POST("rest/account/transaction/log/count")
//    fun logCount(): Call<LogCountResponseModel>
}