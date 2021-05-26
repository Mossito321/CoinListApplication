package com.mossito.coinlist.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mossito.coinlist.databinding.ItemCoinDetailBinding
import com.mossito.coinlist.databinding.ItemCoinImageBinding
import com.mossito.coinlist.domain.model.CoinDisplayModel
import com.mossito.coinlist.presentation.viewholder.CoinDetailViewHolder
import com.mossito.coinlist.presentation.viewholder.CoinImageViewHolder

class CoinListAdapter : RecyclerView.Adapter<CoinListAdapter.CoinListViewHolder>() {

    private var coinItemList = mutableListOf<CoinDisplayModel>()

    companion object {
        private const val VIEW_TYPE_DEFAULT = 1
        private const val VIEW_TYPE_ONLY_IMAGE = 2
    }

    abstract class CoinListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(
            coinDisplayModel: CoinDisplayModel,
            position: Int
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_ONLY_IMAGE -> {
                val binding = ItemCoinImageBinding.inflate(layoutInflater, parent, false)
                CoinImageViewHolder(binding)
            }
            else -> {
                val binding = ItemCoinDetailBinding.inflate(layoutInflater, parent, false)
                CoinDetailViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: CoinListViewHolder, position: Int) {
        when (getItemViewType(position)) {
            VIEW_TYPE_ONLY_IMAGE ->
                holder.bind(coinDisplayModel = coinItemList[position], position)
            else ->
                holder.bind(coinDisplayModel = coinItemList[position], position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if ((position + 1) % 5 == 0) {
            VIEW_TYPE_ONLY_IMAGE
        } else {
            VIEW_TYPE_DEFAULT
        }
    }

    override fun getItemCount(): Int {
        return coinItemList.size
    }

    fun setCoinItemList(coinList: List<CoinDisplayModel>) {
        coinItemList.clear()
        coinItemList.addAll(coinList)
        notifyDataSetChanged()
    }

    fun updateCoinItemList(coinList: List<CoinDisplayModel>) {
        coinItemList.addAll(coinList)
        notifyDataSetChanged()
    }

    fun clearCoinItemList() {
        coinItemList.clear()
    }
}
