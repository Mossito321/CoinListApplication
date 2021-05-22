package com.mossito.coinlist.data.repository

import com.mossito.coinlist.api.CoinListApi
import com.mossito.coinlist.data.model.CoinListModel
import io.reactivex.Observable

interface CoinListDataRepository {
    fun getCoinList(): Observable<CoinListModel>
}

class CoinListDataRepositoryImpl(private val api: CoinListApi) : CoinListDataRepository{
    override fun getCoinList(): Observable<CoinListModel> {
        return api.getCoinList()
    }
}