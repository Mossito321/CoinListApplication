package com.mossito.coinlist.data.model

import com.google.gson.annotations.SerializedName

class Base {
    @SerializedName("symbol")
    var symbol: String? = null
    @SerializedName("sign")
    var sign: String? = null
}