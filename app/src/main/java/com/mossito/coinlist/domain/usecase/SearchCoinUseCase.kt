package com.mossito.coinlist.domain.usecase

import com.mossito.coinlist.data.model.CoinList
import com.mossito.coinlist.data.repository.CoinListDataRepository
import com.mossito.coinlist.domain.model.CoinDisplayModel
import io.reactivex.Observable

interface SearchCoinUseCase {
    fun execute(keyword: String): Observable<List<CoinDisplayModel>>
}

class SearchCoinUseCaseImpl(private val coinListDataRepository: CoinListDataRepository) :
    SearchCoinUseCase {

    override fun execute(keyword: String): Observable<List<CoinDisplayModel>> {
        return coinListDataRepository.getCoinList()
            .map { response ->
                response.data?.coinList?.let { coinList -> mapToDomainModel(keyword, coinList) }
            }
    }

    private fun mapToDomainModel(
        keyword: String,
        coinList: List<CoinList>
    ): List<CoinDisplayModel> {
        val filterDataList = mutableListOf<CoinDisplayModel>()
        coinList.forEach { _coin ->
            val coinName = _coin.name?.let {
                it.toLowerCase()
            } ?: ""
            if (coinName.contains(keyword)) {
                filterDataList.add(CoinDisplayModel().apply {
                    this.coinName = _coin.name ?: ""
                    this.coinDetail = _coin.description ?: ""
                    this.coinImageUrl = _coin.iconUrl ?: ""
                })
            }
        }
        return filterDataList
    }
}