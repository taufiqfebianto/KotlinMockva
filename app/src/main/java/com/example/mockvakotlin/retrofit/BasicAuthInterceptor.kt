package com.example.mockvakotlin.retrofit

import com.example.mockvakotlin.BuildConfig
import com.example.mockvakotlin.sharedpref.Constant
import okhttp3.Credentials
import okhttp3.Interceptor

class BasicAuthInterceptor : Interceptor {

    val API_KEY = BuildConfig.API_KEY
    val SECRET_KEY = BuildConfig.SECRET_KEY

    //    private var credentials: String = Credentials.basic(Constant.API_KEY, Constant.SECRET_KEY)
    private var credentials: String = Credentials.basic(API_KEY, SECRET_KEY)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        request = request.newBuilder().header("Authorization", credentials).build()

        return chain.proceed(request)
    }
}