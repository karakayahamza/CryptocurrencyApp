package com.example.cryptocurrencyapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptocurrencyapp.models.CurrencyModel
import com.example.cryptocurrencyapp.R
import com.example.cryptocurrencyapp.databinding.RowLayoutBinding
import androidx.core.os.bundleOf
import androidx.navigation.findNavController


class RecyclerViewAdapter(private var cryptoList: ArrayList<CurrencyModel.Coin>) : RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>(){

    private val color: Int = R.color.UnhappyPurple

     @SuppressLint("NotifyDataSetChanged")
     fun filterList(filtered: ArrayList<CurrencyModel.Coin>) {
        cryptoList = filtered
        notifyDataSetChanged()
    }

    class RowHolder(val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RowHolder(binding)
    }

    override fun getItemCount(): Int {
        return cryptoList.count()
    }

    @SuppressLint("ResourceType", "SetTextI18n")
    override fun onBindViewHolder(holder: RowHolder, @SuppressLint("RecyclerView") position: Int) {

        holder.itemView.setOnClickListener {
            
            val bundle1 = bundleOf("currency" to cryptoList[position])
            it.findNavController().navigate(R.id.action_currenciesFragment_to_convertFragment,bundle1)

        }

        holder.itemView.setBackgroundColor(color)
        holder.binding.name.text = cryptoList[position].name
        holder.binding.price.text = cryptoList[position].price+"$"
        Glide.with(holder.itemView.context).load(cryptoList[position].iconUrl).into(holder.binding.image)
    }
}