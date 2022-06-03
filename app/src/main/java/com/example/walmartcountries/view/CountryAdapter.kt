package com.example.walmartcountries.view

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.walmartcountries.R
import com.example.walmartcountries.databinding.CountryListItemBinding
import com.example.walmartcountries.model.CountryItem

class CountryAdapter(
    private val countries: MutableList<CountryItem> = mutableListOf()
    ): RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {
    inner class CountryViewHolder(
        private val binding: CountryListItemBinding
        ): RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: CountryItem) {
            binding.apply {
                tvCountryName.text = item.getNameAndRegion()
                tvCountryCapital.text = item.capital
                tvCountryCode.text = item.code
                Glide.with(ivFlag)
                    .load(item.getFlagImage())
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            pbLoading.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            pbLoading.visibility = View.GONE
                            return false
                        }

                    })
                    .into(ivFlag)
            }
        }
    }

    fun setCountryList(newList: List<CountryItem>) {
        countries.apply {
            clear()
            addAll(newList)
            sortBy { it.name }
        }
        notifyItemRangeChanged(0,itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CountryViewHolder(
            CountryListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.onBind(countries[position])
    }

    override fun getItemCount() = countries.size
}