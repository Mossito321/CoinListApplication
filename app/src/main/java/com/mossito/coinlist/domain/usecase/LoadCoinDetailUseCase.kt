package com.mossito.coinlist.domain.usecase

import com.mossito.coinlist.data.model.CoinList
import com.mossito.coinlist.data.repository.CoinListDataRepository
import com.mossito.coinlist.domain.model.CoinDisplayModel
import io.reactivex.Observable

interface LoadCoinDetailUseCase {
    fun execute(): Observable<List<CoinDisplayModel>>
}

class LoadCoinDetailUseCaseImpl(private val coinListDataRepository: CoinListDataRepository) :
    LoadCoinDetailUseCase {
    override fun execute(): Observable<List<CoinDisplayModel>> {
        return coinListDataRepository.getCoinList()
            .map { response ->
                response.data?.coinList?.let { coinList -> mapToDomainModel(coinList) }
            }
    }

    private fun mapToDomainModel(coinList: List<CoinList>): List<CoinDisplayModel> {
        val searchDataList = mutableListOf<CoinDisplayModel>()
        coinList.forEach { _coin ->
            searchDataList.add(CoinDisplayModel().apply {
                coinName = _coin.name ?: ""
                coinDetail = _coin.description ?: ""
                coinImageUrl = _coin.iconUrl ?: ""
            })
        }
        return searchDataList
    }
}