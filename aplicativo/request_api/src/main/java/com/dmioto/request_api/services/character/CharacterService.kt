package com.dmioto.request_api.services.character

import android.content.Context
import com.dmioto.request_api.BaseService
import com.dmioto.request_api.ConnectionListener
import com.dmioto.request_api.models.Character
import com.dmioto.request_api.services.RequestCommonQuery.Companion.QUERY_LIMIT_DEFAULT_QTY

class CharacterService (private val context: Context) {

    companion object {
        /**
         * How many objects can come on a request. Default is [QUERY_LIMIT_DEFAULT_QTY].
         * **/
        var requestQtyLimit: Int = QUERY_LIMIT_DEFAULT_QTY
    }

    private val serviceApi by lazy {
        BaseService(context, CharacterServiceContract::class.java)
    }


    /**
     * Get all characters given the [requestQtyLimit] and paging.
     * @param offset is the object index from the hole [Character] list. Act like a paging pattern.
     * @param connectionListener return the list of [Character], an error or a warning that there is no internet.
     * **/
    fun getAllCharacters(offset: Int, connectionListener: ConnectionListener<Character>){
        serviceApi.callServerApi(serviceApi.clientApi.getCharacters(offset, requestQtyLimit), connectionListener)
    }

}