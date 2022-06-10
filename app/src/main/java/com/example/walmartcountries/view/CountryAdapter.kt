package com.example.walmartcountries.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.walmartcountries.R
import com.example.walmartcountries.databinding.CountryListItemBinding
import com.example.walmartcountries.databinding.HeaderListItemBinding
import com.example.walmartcountries.model.CountryItem

class CountryAdapter(
    private val countries: MutableList<Any> = mutableListOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class CountryViewHolder(
        private val binding: CountryListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: CountryItem) {
            binding.apply {
                tvCountryName.text = item.getNameAndRegion()
                tvCountryCapital.text = item.capital
                tvCountryCode.text = item.code
                Glide.with(ivFlag)
                    .load(item.getFlagImage())
                    .placeholder(R.drawable.giphy)
                    .into(ivFlag)
            }
        }
    }

    inner class HeaderViewHolder(
        private val binding: HeaderListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(letter: Char) {
            binding.headerLetter.text = letter.toString()
        }
    }

    companion object {
        const val HEADER = 1
        const val COUNTRY = 2
    }

    fun setCountryList(newList: List<CountryItem>) {
        var currentLetter = ' '
        newList.sortedBy { it.name }.forEach { item ->
            item.name.first().apply {
                if (this != currentLetter) {
                    countries.add(this)
                    currentLetter = this
                }
                countries.add(item)
            }
        }
        notifyItemRangeChanged(0, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            HEADER -> HeaderViewHolder(
                HeaderListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> {
                CountryViewHolder(
                    CountryListItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.onBind(countries[position] as Char)
            is CountryViewHolder -> holder.onBind(countries[position] as CountryItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (countries[position]) {
            is Char -> HEADER
            else -> COUNTRY
        }
    }

    override fun getItemCount() = countries.size
}