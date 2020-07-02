package com.dmioto.request_api.retrofit

import com.dmioto.request_api.CompanionKeys.Companion.PUBLIC_KEY_API
import com.dmioto.request_api.auth.HashGenerator
import okhttp3.Interceptor
import okhttp3.Response

/**
 * This class abstracts the need to every time set the authentication factors needed to call the API
 * like: timestamp, generated public key and md5 hash.
 * **/
internal class AddQueryHashInterceptor: Interceptor {

    private companion object {
        const val QUERY_TIMESTAMP = "ts"
        const val QUERY_API_KEY = "apikey"
        const val QUERY_HASH = "hash"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        // add the authentication factors
        getStringTimestamp().run {
            val newRequestUrl = request.url.newBuilder()
                .addQueryParameter(QUERY_TIMESTAMP, this)
                .addQueryParameter(QUERY_API_KEY, PUBLIC_KEY_API)
                .addQueryParameter(
                    QUERY_HASH, HashGenerator(
                        this
                    ).md5)
                .build()

            request = request.newBuilder().url(newRequestUrl).build()
            return chain.proceed(request)
        }
    }


    private fun getStringTimestamp(): String {
        return System.currentTimeMillis().toString()
    }

}