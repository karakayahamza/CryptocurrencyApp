package com.example.cryptocurrencyapp.services

import com.example.cryptocurrencyapp.models.ConvertCurrencyModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ConvertApi{
    @GET("convert?")
    fun getData(@Query("apikey") apikey: String,
                @Query("to") to : String,
                @Query("from") from : String,
                @Query("amount") amount : String): Observable<ConvertCurrencyModel>
}