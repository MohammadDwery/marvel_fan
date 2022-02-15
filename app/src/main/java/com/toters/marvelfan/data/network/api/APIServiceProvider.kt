package com.toters.marvelfan.data.network.api

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.toters.marvelfan.BuildConfig
import com.toters.marvelfan.BuildConfig.PRIVATE_API_KEY
import com.toters.marvelfan.BuildConfig.PUBLIC_API_KEY
import com.toters.marvelfan.data.model.ApiException
import com.toters.marvelfan.data.network.resposes.ErrorResponse
import com.toters.marvelfan.utils.NetworkConnectionInterceptor
import com.toters.marvelfan.utils.getMd5Hash
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.ResponseBody
import retrofit2.Converter


class APIServiceProvider {
    fun provide(context: Context, baseUrl: String): Retrofit {
        val client = OkHttpClient.Builder()
            .handleErrors()
            .setNetworkInterceptor(context = context)
            .setLogger(HttpLoggingInterceptor.Level.BODY)
//            .setApiKey()
            .setQueryParams()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(client)
            .setConverters()
            .baseUrl(baseUrl)
            .build()
    }

    private fun Retrofit.Builder.setConverters() =
        addConverterFactory(GsonConverterFactory.create())

    private fun OkHttpClient.Builder.setLogger(
        logLevel: HttpLoggingInterceptor.Level
    ): OkHttpClient.Builder {
        if (BuildConfig.DEBUG) {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = logLevel
            })
        }
        return this
    }

//    private fun OkHttpClient.Builder.setApiKey() = addInterceptor { chain ->
//        val request = chain.request().newBuilder().
//            .header("Accept", "application/json")
//            .addHeader("X-CMC_PRO_API_KEY", BuildConfig.API_KEY)
//        chain.proceed(request.build())
//    }

    private fun OkHttpClient.Builder.setNetworkInterceptor(context: Context) : OkHttpClient.Builder =
        addInterceptor(NetworkConnectionInterceptor(context))


    private fun OkHttpClient.Builder.setQueryParams() = addInterceptor { chain ->
        val url = chain.request().url.newBuilder()
            .addQueryParameter("ts", "1")
            .addQueryParameter("apikey", BuildConfig.PUBLIC_API_KEY)
            .addQueryParameter("hash", getMd5Hash("1$PRIVATE_API_KEY$PUBLIC_API_KEY"))
            .build()
        val request = chain.request().newBuilder().url(url)
        chain.proceed(request.build())
    }

    private fun OkHttpClient.Builder.handleErrors() = addInterceptor { chain ->
        val response = chain.proceed(chain.request())

        if (response.isSuccessful) {
            response
        } else {
            val code = response.code
            val body = response.body

            throw if (body == null) {
                ApiException(ErrorResponse(code.toString(), "Server response is empty"))
            } else {
                try {
                    val gson = Gson()
                    val errorResponse: ErrorResponse = gson.fromJson(response.body!!.charStream(), ErrorResponse::class.java)
                    ApiException(errorResponse)
                } catch (ex: Exception) {
                    ApiException(ErrorResponse(code.toString(), "Cannot parse error: ${ex.message}"))
                } finally {
                    body.close()
                }
            }
        }
    }
}