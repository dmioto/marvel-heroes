package com.dmioto.request_api.models

/**
 * Response pattern:
"thumbnail": {
"path": "http://i.annihil.us/u/prod/marvel/i/mg/7/c0/58efe859632e8",
"extension": "jpg"
}
 * **/

class Thumbnail(val path: String, val extension: String){

    val fullUrl get() = "$path.$extension"

}