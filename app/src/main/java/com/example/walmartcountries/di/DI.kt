package com.example.walmartcountries.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.walmartcountries.api.ApiService
import com.example.walmartcountries.api.CountryRepositoryImpl
import com.example.walmartcountries.viewmodel.CountryViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DI {
    private val service = Retrofit.Builder()
        .baseUrl(ApiService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(provideOkHttpClient())
        .build()
        .create(ApiService::class.java)

    private fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

    private fun provideRepository() = CountryRepositoryImpl(service)

    fun buildViewModel(owner: ViewModelStoreOwner): CountryViewModel =
        ViewModelProvider(owner, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CountryViewModel(provideRepository()) as T
            }
        })[CountryViewModel::class.java]
}