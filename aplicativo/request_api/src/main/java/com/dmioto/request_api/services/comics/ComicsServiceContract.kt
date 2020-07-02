package com.dmioto.request_api.services.comics

import com.dmioto.request_api.response.Response
import com.dmioto.request_api.models.Comic
import com.dmioto.request_api.services.RequestCommonQuery.Companion.QUERY_LIMIT
import com.dmioto.request_api.services.RequestCommonQuery.Companion.QUERY_LIMIT_DEFAULT_QTY
import com.dmioto.request_api.services.RequestCommonQuery.Companion.QUERY_OFFSET
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface ComicsServiceContract {

    private companion object {
        const val PATH_CHARACTER_ID = "characterId"
    }


    /**
     *  Get all the comics from a given character by [characterId] in request page pattern.
     *  @param characterId is the id from [com.nschirmer.response_objects.Character].
     *  @param offset is the index of the requested object list, kind like the "page" from the request.
     *  @param limit (optional) the quantity of returned objects. The max is 100 on the API side. The default is [QUERY_LIMIT_DEFAULT_QTY] items.
     *
     *  @return a [Call] of the HTTP request with the object type from the API.
     *  **/
    @GET("characters/{$PATH_CHARACTER_ID}/comics")
    fun getComics(@Path(PATH_CHARACTER_ID) characterId: Int,
                  @Query(QUERY_OFFSET) offset: Int,
                  @Query(QUERY_LIMIT) limit: Int = QUERY_LIMIT_DEFAULT_QTY):
            Call<Response<Comic>>


}