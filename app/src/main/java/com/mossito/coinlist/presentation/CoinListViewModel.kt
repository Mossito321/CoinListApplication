package com.mossito.coinlist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mossito.coinlist.domain.model.CoinDisplayModel
import com.mossito.coinlist.domain.usecase.LoadCoinDetailUseCase
import com.mossito.coinlist.domain.usecase.LoadCoinDetailWithLimitUseCase
import com.mossito.coinlist.domain.usecase.SearchCoinUseCase
import com.mossito.coinlist.extention.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CoinListViewModel(
    private val loadCoinDetailWithLimitUseCase: LoadCoinDetailWithLimitUseCase,
    private val searchCoinUseCase: SearchCoinUseCase
) : ViewModel() {

    companion object {
        private const val INITIAL_OFFSET = 0
        private const val COIN_LIMIT = 10
    }

    fun coinListToShow() = coinList
    fun coinListLoadMore() = coinListLoadMore
    fun showError() = showError

    private val coinList = MutableLiveData<List<CoinDisplayModel>>()
    private val coinListLoadMore = MutableLiveData<List<CoinDisplayModel>>()
    private val showError = MutableLiveData<Unit>()
    private val disposeBag = CompositeDisposable()

    private var currentOffset: Int = INITIAL_OFFSET

    fun loadCoinList() {
        loadCoinDetailWithLimitUseCase.execute(offset = INITIAL_OFFSET, limit = COIN_LIMIT)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ _coinList ->
                coinList.value = _coinList
                currentOffset += 10
            }, {
                showError.value = Unit
            })
            .addTo(disposeBag)
    }

    fun loadDefaultCoinList() {
        currentOffset = INITIAL_OFFSET
        loadCoinDetailWithLimitUseCase.execute(offset = currentOffset, limit = COIN_LIMIT)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ _coinList ->
                coinList.value = _coinList
                currentOffset += 10
            }, {
                showError.value = Unit
            })
            .addTo(disposeBag)
    }

    fun loadMoreCoinList() {
        loadCoinDetailWithLimitUseCase.execute(offset = currentOffset, limit = COIN_LIMIT)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ _coinList ->
                coinListLoadMore.value = _coinList
                currentOffset += 10
            }, {})
            .addTo(disposeBag)
    }

    fun searchCoin(keyword: String) {
        searchCoinUseCase.execute(keyword)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ _coinList ->
                coinList.value = _coinList
            }, {})
            .addTo(disposeBag)
    }
}