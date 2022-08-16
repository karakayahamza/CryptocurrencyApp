package com.example.cryptocurrencyapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurrencyapp.adapters.RecyclerViewAdapter
import com.example.cryptocurrencyapp.viewModel.CurrenciesViewModel
import com.example.cryptocurrencyapp.models.CurrencyModel
import com.example.cryptocurrencyapp.databinding.FragmentCurrenciesBinding
import io.reactivex.disposables.CompositeDisposable
import android.widget.Toast

class CurrenciesFragment : Fragment(){
    private var _binding : FragmentCurrenciesBinding? = null
    private val binding get() = _binding!!
    private var cryptoModelsList : ArrayList<CurrencyModel.Coin>? = null
    private var compositeDisposable: CompositeDisposable? = null
    private var layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(context)
    private lateinit var viewModel: CurrenciesViewModel
    private var recyclerViewAdapter  : RecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrenciesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        compositeDisposable = CompositeDisposable()
        binding.recyclerView.layoutManager = layoutManager

        viewModel = ViewModelProviders.of(this)[CurrenciesViewModel::class.java]
        viewModel.loadData()

        observeLiveData()

        binding.idSV.setOnQueryTextListener(object  : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                filter(p0.toString())
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                filter(p0.toString())
                return true
            }

        })
    }
    private fun observeLiveData(){
        viewModel.currencies.observe(viewLifecycleOwner,{ currency ->
            currency?.let {
                cryptoModelsList = it.data.coins!!
                cryptoModelsList?.let {

                    recyclerViewAdapter = RecyclerViewAdapter(it)
                    binding.recyclerView.adapter = recyclerViewAdapter
                }
            }
        })
        viewModel.error.observe(viewLifecycleOwner,{
            it?.let {
               Toast.makeText(requireContext(),"Something went wrong",Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable?.clear()
    }

    private fun filter(filter: String) {

        val filteredlist: ArrayList<CurrencyModel.Coin> = ArrayList()
        for (item in cryptoModelsList!!) {

            if (item.name!!.lowercase().contains(filter.lowercase())){
                filteredlist.add(item)
            }
        }

        if (filteredlist.isEmpty()) {
            Toast.makeText(requireContext(), "No currency found.", Toast.LENGTH_SHORT).show()
        } else {
            recyclerViewAdapter?.filterList(filteredlist)

        }
    }
}