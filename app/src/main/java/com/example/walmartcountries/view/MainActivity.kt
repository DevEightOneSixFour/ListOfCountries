package com.example.walmartcountries.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.walmartcountries.R
import com.example.walmartcountries.databinding.ActivityMainBinding
import com.example.walmartcountries.di.DI

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val countryAdapter by lazy {
        CountryAdapter()
    }

    private val viewModel by lazy {
        DI.buildViewModel(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        configureObserver()
    }

    private fun configureObserver() {
        viewModel.countriesLiveData.observe(this) {
            when(it) {
                is ResponseStatus.SUCCESS -> {
                    countryAdapter.setCountryList(it.data)
                    binding.listOfCountries.adapter = countryAdapter
                }
                is ResponseStatus.ERROR -> {
                    binding.tvErrorText.text = it.exception.message
                }
                else -> {}
            }
        }
    }
}