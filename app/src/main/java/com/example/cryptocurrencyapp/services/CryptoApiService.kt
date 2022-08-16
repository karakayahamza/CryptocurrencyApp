package com.example.cryptocurrencyapp.services

import com.example.cryptocurrencyapp.models.CurrencyModel
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CryptoApiService {

    private val URL = "https://coinranking1.p.rapidapi.com/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CryptoAPI::class.java)

    fun getData(): Observable<CurrencyModel> {
        return retrofit.getData()
    }
}