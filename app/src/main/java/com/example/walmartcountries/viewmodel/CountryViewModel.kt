package com.example.walmartcountries.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.walmartcountries.api.CountryRepository
import com.example.walmartcountries.view.ResponseStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CountryViewModel(private val repository: CountryRepository): ViewModel() {

    private val _countriesLiveData = MutableLiveData<ResponseStatus>()
    val countriesLiveData: LiveData<ResponseStatus> get() = _countriesLiveData

    init {
        getAllCountries()
    }

    private fun getAllCountries() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.getAllCountries().collect {
                _countriesLiveData.postValue(it)
            }
        }
    }
}