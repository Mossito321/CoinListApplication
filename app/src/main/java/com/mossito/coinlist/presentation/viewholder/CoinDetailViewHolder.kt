package com.mossito.coinlist.presentation.viewholder

import android.app.Activity
import android.content.Context
import android.text.Html
import com.ahmadrosid.svgloader.SvgLoader
import com.mossito.coinlist.databinding.ItemCoinDetailBinding
import com.mossito.coinlist.domain.model.CoinDisplayModel
import com.mossito.coinlist.presentation.adapter.CoinListAdapter
import com.squareup.picasso.Picasso

class CoinDetailViewHolder(
    private val binding: ItemCoinDetailBinding
) : CoinListAdapter.CoinListViewHolder(binding.root) {

    override fun bind(coinDisplayModel: CoinDisplayModel, position: Int) {
        itemView.apply {
            binding.coinNameTextView.text = coinDisplayModel.coinName
            binding.coinDetailTextView.text = Html.fromHtml(coinDisplayModel.coinDetail).toString()
            if (coinDisplayModel.coinImageUrl.contains(".svg")) {
                loadSvgImage(context, coinDisplayModel.coinImageUrl)
            } else {
                loadPngImage(coinDisplayModel.coinImageUrl)
            }
        }
    }

    private fun loadSvgImage(context: Context, url: String) {
        SvgLoader.pluck()
            .with(context as Activity)
            .load(url, binding.coinImageView)
    }

    private fun loadPngImage(url: String) {
        Picasso.get().load(url).into(binding.coinImageView)
    }
}
