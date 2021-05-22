package com.mossito.coinlist.data.model

import com.google.gson.annotations.SerializedName

class CoinListModel{
    @SerializedName("data")
    var data: Data? = null
}

class Status {
    @SerializedName("total")
    var total: Int? = null
    @SerializedName("offset")
    var offset: Int? = null
    @SerializedName("limit")
    var limit: Int? = null
    @SerializedName("order")
    var order: String? = null
    @SerializedName("base")
    var base: String? = null
}