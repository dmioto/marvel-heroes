package com.dmioto.request_api.services.comics

import android.content.Context
import com.dmioto.request_api.BaseService
import com.dmioto.request_api.ConnectionListener
import com.dmioto.request_api.models.Comic
import com.dmioto.request_api.services.RequestCommonQuery.Companion.QUERY_LIMIT_DEFAULT_QTY

class ComicsService (private val context: Context) {

    companion object {
        /**
         * How many objects can come on a request. Default is [QUERY_LIMIT_DEFAULT_QTY].
         * **/
        var requestQtyLimit: Int = QUERY_LIMIT_DEFAULT_QTY
    }

    private val serviceApi by lazy {
        BaseService(context,ComicsServiceContract::class.java)
    }


    /**
     * Get all comics from a character given the [requestQtyLimit] and paging.
     * @param characterId is the id from [com.nschirmer.response_objects.Character].
     * @param offset is the object index from the hole [Comic] list. Act like a paging pattern.
     * @param connectionListener return the list of [Comic], an error or a warning that there is no internet.
     * **/
    fun getComics(characterId: Int, offset: Int, connectionListener: ConnectionListener<Comic>){
        serviceApi.callServerApi(serviceApi.clientApi.getComics(characterId, offset, requestQtyLimit), connectionListener)
    }

}