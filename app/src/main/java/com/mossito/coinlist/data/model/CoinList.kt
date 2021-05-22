package com.mossito.coinlist.data.model

import com.google.gson.annotations.SerializedName

class CoinList {
    @SerializedName("id")
    var id: Int? = null
    @SerializedName("uuid")
    var uuid: String? = null
    @SerializedName("slug")
    var slug: String? = null
    @SerializedName("symbol")
    var symbol: String? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("description")
    var description: String? = null
    @SerializedName("color")
    var color: String? = null
    @SerializedName("iconType")
    var iconType: String? = null
    @SerializedName("iconUrl")
    var iconUrl: String? = null
    @SerializedName("websiteUrl")
    var websiteUrl: String? = null
    @SerializedName("socials")
    var socials: List<Social>? = null
    @SerializedName("links")
    var links: List<Link>? = null
    @SerializedName("confirmedSupply")
    var comFirmSupply: Boolean? = false
    @SerializedName("numberOfMarkets")
    var numberOfMarket: Int? = null
    @SerializedName("numberOfExchanges")
    var numberOfExchanges: Int? = null
    @SerializedName("type")
    var type: String? = null
}

class Social {
    @SerializedName("name")
    var name: String? = null
    @SerializedName("url")
    var url: String? = null
    @SerializedName("type")
    var type: String? = null
}

class Link {
    @SerializedName("name")
    var name: String? = null
    @SerializedName("url")
    var url: String? = null
    @SerializedName("type")
    var type: String? = null
}