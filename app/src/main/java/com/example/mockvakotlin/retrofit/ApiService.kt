package com.example.mockvakotlin.retrofit

import com.example.mockvakotlin.sharedpref.Constant
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiService {

    val endpoint: ApiEndpoint
        get() {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val certificatePinner = CertificatePinner.Builder()
                .add(
                    "mockva.daksa.co.id",
                    "sha256/iuJMm3sJB9RT0oOtxIB+/miBYljfNOCIiiJo0dnL8s="
                ).build()


            val client = OkHttpClient.Builder()
                .certificatePinner(certificatePinner)
                .addInterceptor(BasicAuthInterceptor())
                .addInterceptor(interceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiEndpoint::class.java)

        }

}