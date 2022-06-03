package com.example.walmartcountries.api

import com.example.walmartcountries.view.ResponseStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface CountryRepository {
    suspend fun getAllCountries(): Flow<ResponseStatus>
}

class CountryRepositoryImpl(private val service: ApiService): CountryRepository {
    override suspend fun getAllCountries() =
        flow {
            try {
                val response = service.getAllCountries()
                if (response.isSuccessful) {
                    emit(
                        response.body()?.let { ResponseStatus.SUCCESS(it) }
                            ?: throw Exception("Null response")
                    )
                } else throw Exception("Failed network call")
            } catch (e: Exception) {
                emit(ResponseStatus.ERROR(e))
            }
        }

}