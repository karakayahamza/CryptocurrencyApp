package com.example.cryptocurrencyapp.services

import com.example.cryptocurrencyapp.models.CurrencyModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers

interface CryptoAPI {
    @Headers("X-RapidAPI-Key:API_KEYHERE","X-RapidAPI-Host:coinranking1.p.rapidapi.com")
    @GET("coins?referenceCurrencyUuid=yhjMzLPhuIDl&timePeriod=24h&tiers%5B0%5D=1&orderBy=marketCap&orderDirection=desc&limit=50&offset=0")
    fun getData(): Observable<CurrencyModel>
}
