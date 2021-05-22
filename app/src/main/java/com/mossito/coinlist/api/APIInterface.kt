package com.mossito.coinlist.api

import com.mossito.coinlist.data.model.CoinListModel
import io.reactivex.Observable
import retrofit2.http.GET

interface APIInterface {
    @GET("/v1/public/coins")
    fun getCoinList(): Observable<CoinListModel>
}