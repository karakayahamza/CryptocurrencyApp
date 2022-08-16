package com.example.cryptocurrencyapp.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CurrencyModel(var data : Data):Serializable{

    class Coin(var uuid: String? = null,
               var symbol: String? = null,
               var name: String? = null,
               var color: String? = null,
               @SerializedName("iconUrl")
               var iconUrl: String? = null,
               var marketCap: String? = null,
               var price: String? = null,
               var listedAt: Int = 0,
               var tier: Int = 0,
               var change: String? = null,
               var rank: Int = 0,
               var sparkline: ArrayList<String>? = null,
               var lowVolume: Boolean = false,
               var coinrankingUrl: String? = null,
               var _24hVolume: String? = null,
               var btcPrice: String? = null):Serializable

    class Data(var stats: Stats? = null,
               var coins: ArrayList<Coin>? = null)

    class Root(var status: String? = null,
               var data: Data? = null)

    class Stats(var total: Int = 0,
                var totalCoins: Int = 0,
                var totalMarkets: Int = 0,
                var totalExchanges: Int = 0,
                var totalMarketCap: String? = null,
                var total24hVolume: String? = null)
}