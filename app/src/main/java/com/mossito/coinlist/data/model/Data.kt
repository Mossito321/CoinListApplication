package com.mossito.coinlist.data.model

import com.google.gson.annotations.SerializedName

class Data {
    @SerializedName("stats")
    var stats: Status? = null
    @SerializedName("base")
    var base: Base? = null
    @SerializedName("coins")
    var coinList: List<CoinList>? = null
}