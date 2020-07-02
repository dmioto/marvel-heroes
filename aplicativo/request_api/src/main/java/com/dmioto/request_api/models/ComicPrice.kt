package com.dmioto.request_api.models

/**
ComicPrice {
type (string, optional): A description of the price (e.g. print price, digital price).,
price (float, optional): The price (all prices in USD).
}
 */

class ComicPrice(val type: String,
                 val price: Float)