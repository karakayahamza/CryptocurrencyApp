package com.example.cryptocurrencyapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cryptocurrencyapp.models.CurrencyModel
import com.example.cryptocurrencyapp.services.CryptoApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CurrenciesViewModel : ViewModel() {
    private var compositeDisposable: CompositeDisposable? = null
    private val cryptoApiService = CryptoApiService()

    val currencies = MutableLiveData<CurrencyModel>()
    val error = MutableLiveData<Boolean>()
    fun loadData(){
        compositeDisposable = CompositeDisposable()
        compositeDisposable?.add(cryptoApiService.getData()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                    currencies.value = it
            },{
                error.value = false
            }))
    }

}