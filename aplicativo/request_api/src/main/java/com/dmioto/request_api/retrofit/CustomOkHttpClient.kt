package com.dmioto.request_api.retrofit

import okhttp3.OkHttpClient

internal class CustomOkHttpClient {

    /** @return OkHttpClient with [AddQueryHashInterceptor] **/
    val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder().let { clientBuilder ->
            clientBuilder.addInterceptor(AddQueryHashInterceptor())
            clientBuilder.build()
        }
    }

}