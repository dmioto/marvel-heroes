package com.dmioto.request_api.retrofit

import android.content.Context
import com.dmioto.request_api.ConnectionListener
import com.dmioto.request_api.response.Response
import retrofit2.Call
import retrofit2.Callback



/**
 * Custom service callback receiving [Response] as object and check if there is an error.
 *
 * Return all objects and status through [callback] @see [ConnectionListener].
 *
 * WARNING: If the service response changes the [Response] structure, this class will not work and you will always get [ConnectionListener.onFail] status.
 *
 * [R] is the [Response] object received on the [retrofit2.Callback].
 * [T] is the object type that is expected in the [retrofit2.Response].
 * **/
internal class Callback <T, R> (private val context: Context, private val callback: ConnectionListener<T>): Callback<R> {


    private val castError: String by lazy {
        context.getString(com.dmioto.request_api.R.string.cast_error)
    }

    private val failError: String by lazy {
        context.getString(com.dmioto.request_api.R.string.not_able_to_connect_error)
    }


    private val requestFailError: String by lazy {
        context.getString(com.dmioto.request_api.R.string.request_failed)
    }


    /**
     *  Response from the server.
     *
     *  Check if the [retrofit2.Response] came with http success and no null [retrofit2.Response.body].
     *  If a error will be found, it will send the to string containing an error.
     *  If there is an cast error, it will send [ConnectionListener.onFail] status.
     *  **/
    override fun onResponse(call: Call<R>, response: retrofit2.Response<R>) {
        return when {
            responseIsSuccessful(response) -> processResponseData(response)
            else -> callback.onFail(requestFailError)
        }
    }


    /** It will trigger this if there is any server communication error or an [ChangeObjectNameInterceptor] error. **/
    override fun onFailure(call: Call<R>, t: Throwable) {
        callback.onFail(failError)
    }


    /**
     * Check if the [retrofit2.Response] was successful and has any [Response.data] in it.
     * **/
    private fun responseIsSuccessful(response: retrofit2.Response<R>): Boolean {
        return response.isSuccessful && when {
            response.body() == null -> false
            else -> {
                val responseCasted: retrofit2.Response<Response<T>>? = cast(response)
                responseCasted?.body()?.data != null
            }
        }
    }


    /** Try to cast the [Response.data] object. **/
    private fun processResponseData(response: retrofit2.Response<R>){
        getDataResponse(cast(response.body())).run{
            when {
                this != null -> callback.onSuccess(this)
                else -> callback.onFail(castError)
            }
        }
    }


    private fun getDataResponse(response: Response<T>?): List<T>? {
        return response?.data?.results
    }


    private inline fun <reified T> cast(from: Any?): T? = from as? T

    private fun getResponseCasted(response: retrofit2.Response<T>?): retrofit2.Response<Response<T>>? = cast(response)

}