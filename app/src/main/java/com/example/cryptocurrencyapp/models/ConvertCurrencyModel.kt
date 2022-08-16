package com.example.cryptocurrencyapp.models

import java.io.Serializable

        data class
        ConvertCurrencyModel(var info: Info? = null,
                                    var success: Boolean = false,
                                    var query: Query? = null,
                                    var date: String? = null,
                                    var result: Double = 0.0):Serializable{

        class Info (var timestamp: Int = 0,
                    var rate: Double = 0.0):Serializable

        class Query(var from: String? = null,
                    var myto: String? = null,
                    var amount: Int = 0):Serializable}

