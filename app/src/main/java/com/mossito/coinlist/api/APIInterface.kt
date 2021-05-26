package com.mossito.coinlist.api

import com.mossito.coinlist.data.model.CoinListModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {
    @GET("/v1/public/coins")
    fun getCoinList(): Observable<CoinListModel>

    @GET("/v1/public/coins")
    fun getCoinListWithLimit(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Observable<CoinListModel>
}