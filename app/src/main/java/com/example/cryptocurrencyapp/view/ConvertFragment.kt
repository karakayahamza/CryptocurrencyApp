package com.example.cryptocurrencyapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.cryptocurrencyapp.BuildConfig
import com.example.cryptocurrencyapp.models.ConvertCurrencyModel
import com.example.cryptocurrencyapp.R
import com.example.cryptocurrencyapp.databinding.FragmentConvertBinding
import com.example.cryptocurrencyapp.models.CurrencyModel
import com.example.cryptocurrencyapp.services.ConvertApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import androidx.navigation.findNavController


class ConvertFragment : Fragment(),AdapterView.OnItemSelectedListener {

    private var _binding : FragmentConvertBinding? = null
    private val binding get() = _binding!!
    private val URL = "https://api.apilayer.com/fixer/"
    private var valueOfCurrency : Double? = null
    private var spinner: Spinner? = null
    private var compositeDisposable = CompositeDisposable()
    private var targetCurrency : String? = null
    private var  receivingData : CurrencyModel.Coin? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConvertBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        receivingData = arguments?.getSerializable("currency") as CurrencyModel.Coin

        loadData(receivingData!!)
        spinnerActive()

        binding.calculate.setOnClickListener {
            println(binding.editTextNumber.text.toString())
            loadData(targetCurrency!!, binding.editTextNumber.text.toString())
        }
        binding.back.setOnClickListener {
            val action = ConvertFragmentDirections.actionConvertFragmentToCurrenciesFragment()
            it?.findNavController()?.navigate(action)
            onDestroy() }
    }

    private fun spinnerActive(){
        spinner = binding.spinner
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.CurrencySymbols,
            android.R.layout.simple_spinner_item

        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner!!.adapter = adapter
        }

        spinner!!.setSelection(-1,true)
        spinner!!.onItemSelectedListener = this
    }

    @SuppressLint("SetTextI18n")
    fun loadData(currency :CurrencyModel.Coin){
        binding.currencyname.text = currency.name!!
        binding.result.text = currency.price +"$"
        valueOfCurrency = currency.price.toString().toDouble()

        Glide.with(binding.coinImage).load(currency.iconUrl).into(binding.coinImage)

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        targetCurrency = p0!!.getItemAtPosition(p2).toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        Toast.makeText(requireContext(),"You selected nothing.", Toast.LENGTH_LONG).show()
    }

    private fun loadData(to : String,amount:String){

        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(ConvertApi::class.java)

        compositeDisposable.add(retrofit.getData(BuildConfig.API_KEY,to,"USD",amount)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                handleResponse(it,to)
            },{
                Toast.makeText(requireContext(),"Something went wrong.", Toast.LENGTH_LONG).show()
            }))
    }

    private fun handleResponse(cryptoList : ConvertCurrencyModel, to :String){
        cryptoList.let {
            var symbol : String? = null
            when(to){
                "TRY" -> symbol="₺"
                "EUR" -> symbol="€"
                "JPY" -> symbol="¥"
                "GBP" -> symbol="£"
                "USD" -> symbol="$"
            }
            val result = (cryptoList.result * valueOfCurrency!!).toString()+" "+symbol
            binding.result.text = result
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable
    }
}