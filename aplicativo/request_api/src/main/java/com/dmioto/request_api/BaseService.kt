package com.dmioto.request_api

import android.content.Context
import com.dmioto.device_checker.network_checker.NetworkChecker
import com.dmioto.request_api.CompanionKeys.Companion.BASE_API_URL
import com.dmioto.request_api.retrofit.Callback
import com.dmioto.request_api.response.Response
import retrofit2.Call


/** This class is an abstraction of service choosing with [RequestClient], so that any Service to use **/
class BaseService<T> (private val context: Context, serviceContract: Class<T>) {

    /** A ready to use retrofit object based on the service contract **/
    val clientApi by lazy {
        RequestClient(BASE_API_URL).retrofit.create(serviceContract)
    }


    /**
     * Abstraction of [Callback] that already check if there is internet.
     * @param call expect the [Call] object generated from the service contract.
     * @param connectionListener is a pass through of the rest call state @see [ConnectionListener].
     * **/
    fun <R> callServerApi(call : Call<Response<R>>, connectionListener: ConnectionListener<R>) {
        NetworkChecker(context){ canConnectToInternet, _ ->
            if(canConnectToInternet) call.enqueue(Callback(context, connectionListener))
            else connectionListener.noInternet()
        }
    }

}