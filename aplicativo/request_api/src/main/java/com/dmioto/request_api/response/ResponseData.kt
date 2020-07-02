package com.dmioto.request_api.response

class ResponseData<T>(val offset: Int, val limit: Int, val results: List<T>)