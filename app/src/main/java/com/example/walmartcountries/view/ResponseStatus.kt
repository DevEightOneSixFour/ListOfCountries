package com.example.walmartcountries.view

import com.example.walmartcountries.model.CountryItem
import java.lang.Exception

sealed class ResponseStatus {
    class SUCCESS(val data: List<CountryItem>): ResponseStatus()
    class ERROR(val exception: Exception): ResponseStatus()
}
