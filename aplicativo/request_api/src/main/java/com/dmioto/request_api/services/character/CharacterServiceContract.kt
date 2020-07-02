package com.dmioto.request_api.services.character

import com.dmioto.request_api.response.Response
import com.dmioto.request_api.models.Character
import com.dmioto.request_api.services.RequestCommonQuery.Companion.QUERY_LIMIT
import com.dmioto.request_api.services.RequestCommonQuery.Companion.QUERY_LIMIT_DEFAULT_QTY
import com.dmioto.request_api.services.RequestCommonQuery.Companion.QUERY_OFFSET
import com.dmioto.request_api.services.RequestCommonQuery.Companion.QUERY_ORDER_BY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

internal interface CharacterServiceContract {

    private companion object {
        const val SEARCH_NAME = "nameStartsWith"
        const val ORDER_BY_DEFAULT = "name"
    }

    /**
     *  Get all the characters in request page pattern.
     *  @param offset is the index of the requested object list, kind like the "page" from the request.
     *  @param limit (optional) the quantity of returned objects. The max is 100 on the API side. The default is [QUERY_LIMIT_DEFAULT_QTY] items.
     *  @param orderBy (optional) sort the list between: name, modified, -name, -modified. Default is [ORDER_BY_DEFAULT].
     *
     *  @return a [Call] of the HTTP request with the object type from the API.
     *  **/
    @GET("characters")
    fun getCharacters(@Query(QUERY_OFFSET) offset: Int,
                      @Query(QUERY_LIMIT) limit: Int = QUERY_LIMIT_DEFAULT_QTY,
                      @Query(QUERY_ORDER_BY) orderBy: String = ORDER_BY_DEFAULT):
            Call<Response<Character>>

}