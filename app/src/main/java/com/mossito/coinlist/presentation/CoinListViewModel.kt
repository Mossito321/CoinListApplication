package com.mossito.coinlist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mossito.coinlist.domain.model.CoinDisplayModel
import com.mossito.coinlist.domain.usecase.LoadCoinDetailUseCase
import com.mossito.coinlist.domain.usecase.SearchCoinUseCase
import com.mossito.coinlist.extention.addTo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CoinListViewModel(
    private val loadCoinDetailUseCase: LoadCoinDetailUseCase,
    private val searchCoinUseCase: SearchCoinUseCase
) : ViewModel() {

    fun coinListToShow() = coinList
    fun showError() = showError

    private val coinList = MutableLiveData<List<CoinDisplayModel>>()
    private val showError = MutableLiveData<Unit>()
    private val disposeBag = CompositeDisposable()

    fun loadCoinList() {
        loadCoinDetailUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ _coinList ->
                coinList.value = _coinList
            }, {
                showError.value = Unit
            })
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