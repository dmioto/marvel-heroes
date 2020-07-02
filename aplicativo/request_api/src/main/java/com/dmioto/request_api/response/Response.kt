package com.dmioto.request_api.response

import java.io.Serializable

class Response<T> (val code: Int, val data: ResponseData<T>?) : Serializable