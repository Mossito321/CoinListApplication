package com.mossito.coinlist.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mossito.coinlist.R
import com.mossito.coinlist.databinding.FragmentCoinListBinding

class CoinListFragment : Fragment(R.layout.fragment_coin_list) {

    companion object {
        val TAG = CoinListFragment::class.java.canonicalName as String
    }

    private var _binding: FragmentCoinListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCoinListBinding.inflate(inflater, container, false)
        return binding.root
    }
}