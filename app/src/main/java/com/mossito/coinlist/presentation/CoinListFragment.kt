package com.mossito.coinlist.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mossito.coinlist.R
import com.mossito.coinlist.api.CoinListApi
import com.mossito.coinlist.data.repository.CoinListDataRepositoryImpl
import com.mossito.coinlist.databinding.FragmentCoinListBinding
import com.mossito.coinlist.domain.model.CoinDisplayModel
import com.mossito.coinlist.domain.usecase.LoadCoinDetailUseCaseImpl
import com.mossito.coinlist.domain.usecase.LoadCoinDetailWithLimitUseCaseImpl
import com.mossito.coinlist.domain.usecase.SearchCoinUseCaseImpl
import com.mossito.coinlist.presentation.adapter.CoinListAdapter

class CoinListFragment : Fragment(R.layout.fragment_coin_list) {

    companion object {
        val TAG = CoinListFragment::class.java.canonicalName as String
    }

    private var _binding: FragmentCoinListBinding? = null
    private var disableLoadMore = false
    private val binding get() = _binding!!
    private lateinit var viewModel: CoinListViewModel
    private lateinit var coinListAdapter: CoinListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCoinListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        initView()
    }

    private fun bindViewModel() {
        val coinListDataRepository = CoinListDataRepositoryImpl(CoinListApi)
        val loadCoinDetailWithLimitUseCase =
            LoadCoinDetailWithLimitUseCaseImpl(coinListDataRepository = coinListDataRepository)
        val searchCoinUseCase =
            SearchCoinUseCaseImpl(coinListDataRepository = coinListDataRepository)
        viewModel = CoinListViewModel(
            loadCoinDetailWithLimitUseCase = loadCoinDetailWithLimitUseCase,
            searchCoinUseCase = searchCoinUseCase
        )
        viewModel.loadCoinList()

        with(viewModel) {
            coinListToShow().observe(viewLifecycleOwner, { _coinList ->
                setCoinListData(_coinList)
            })
            coinListLoadMore().observe(viewLifecycleOwner, { _coinList ->
                updateCoinListData(_coinList)
            })
            showError().observe(viewLifecycleOwner, {
                Toast.makeText(context, "Cannot Load Coin This Time.", Toast.LENGTH_LONG).show()
            })
        }
    }

    private fun initView() {
        context?.let { _context ->
            val linearLayoutManager =
                LinearLayoutManager(_context, LinearLayoutManager.VERTICAL, false)
            coinListAdapter = CoinListAdapter()
            coinListAdapter.let {
                binding.coinDetailRecyclerView.layoutManager = linearLayoutManager
                binding.coinDetailRecyclerView.adapter = coinListAdapter
                binding.coinDetailRecyclerView.addItemDecoration(
                    DividerItemDecoration(
                        activity,
                        DividerItemDecoration.VERTICAL
                    )
                )
            }
            binding.swipeRefresh.setOnRefreshListener {
                if (!disableLoadMore) {
                    coinListAdapter.clearCoinItemList()
                    viewModel.loadDefaultCoinList()
                } else {
                    hideSwipeRefresh()
                }
            }
            binding.searchView.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(editable: Editable?) {
                    binding.searchView.let { searchKeywordText ->
                        disableLoadMore = if (editable.toString().isNotEmpty()) {
                            viewModel.searchCoin(searchKeywordText.text.toString())
                            true
                        } else {
                            viewModel.loadDefaultCoinList()
                            false
                        }
                    }
                }

            })
            binding.coinDetailRecyclerView.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val totalCoinItem = linearLayoutManager.itemCount
                    val lastVisibleItem =
                        linearLayoutManager.findLastCompletelyVisibleItemPosition()

                    if (lastVisibleItem == totalCoinItem.minus(1) && !disableLoadMore) {
                        viewModel.loadMoreCoinList()
                    }
                }
            })
        }
    }

    private fun setCoinListData(coinList: List<CoinDisplayModel>) {
        hideSwipeRefresh()
        coinListAdapter.setCoinItemList(coinList)
    }

    private fun updateCoinListData(coinList: List<CoinDisplayModel>) {
        hideSwipeRefresh()
        coinListAdapter.updateCoinItemList(coinList)
    }

    private fun hideSwipeRefresh() {
        binding.swipeRefresh.let { swipeRefreshLayout ->
            if (swipeRefreshLayout.isRefreshing) {
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }
}