package com.mossito.coinlist.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mossito.coinlist.R
import com.mossito.coinlist.api.CoinListApi
import com.mossito.coinlist.data.repository.CoinListDataRepositoryImpl
import com.mossito.coinlist.databinding.FragmentCoinListBinding
import com.mossito.coinlist.domain.usecase.LoadCoinDetailUseCaseImpl

class CoinListFragment : Fragment(R.layout.fragment_coin_list) {

    companion object {
        val TAG = CoinListFragment::class.java.canonicalName as String
    }

    private var _binding: FragmentCoinListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CoinListViewModel

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
        val loadCoinDetailUseCase =
            LoadCoinDetailUseCaseImpl(coinListDataRepository = coinListDataRepository)
        viewModel = CoinListViewModel(loadCoinDetailUseCase = loadCoinDetailUseCase)
        viewModel.loadCoinList()

        with(viewModel) {
            coinListToShow().observe(viewLifecycleOwner, {
                Toast.makeText(context, "Data Coming Soon Boi ${it.first().coinName}.", Toast.LENGTH_LONG).show()
            })
            showError().observe(viewLifecycleOwner, {
                Toast.makeText(context, "Cannot Load Coin This Time.", Toast.LENGTH_LONG).show()
            })
        }
    }

    private fun initView() {

    }
}