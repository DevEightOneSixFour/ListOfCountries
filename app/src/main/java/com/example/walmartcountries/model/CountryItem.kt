package com.example.walmartcountries.model

data class CountryItem(
    val capital: String,
    val code: String,
    val currency: CountryCurrency,
    val flag: String,
    val language: CountryLanguage,
    val name: String,
    val region: String
) {
    fun getNameAndRegion(): String = "$name, $region"
    // since the given endpoint's image links are broken
    //  I found a url from different api for getting the flag
    fun getFlagImage(): String = "https://countryflagsapi.com/png/$code"
}

data class CountryCurrency(
    val code: String,
    val name: String,
    val symbol: String
)

data class CountryLanguage(
    val code: String,
    val name: String
)