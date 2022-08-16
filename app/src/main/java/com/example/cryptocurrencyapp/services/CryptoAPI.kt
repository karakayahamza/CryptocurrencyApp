package com.example.cryptocurrencyapp.services

import com.example.cryptocurrencyapp.models.CurrencyModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers

interface CryptoAPI {
    @Headers("X-RapidAPI-Key:5c51b42087msh722ca1793e0e355p113c84jsncd14f608f0e2","X-RapidAPI-Host:coinranking1.p.rapidapi.com")
    @GET("coins?referenceCurrencyUuid=yhjMzLPhuIDl&timePeriod=24h&tiers%5B0%5D=1&orderBy=marketCap&orderDirection=desc&limit=50&offset=0")
    fun getData(): Observable<CurrencyModel>
}