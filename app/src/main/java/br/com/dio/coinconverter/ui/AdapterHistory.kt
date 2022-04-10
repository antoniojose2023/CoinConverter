package br.com.dio.coinconverter.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.dio.coinconverter.core.extensions.formatCurrency
import br.com.dio.coinconverter.data.enum.Coin
import br.com.dio.coinconverter.data.model.ExChangeResponseValue
import br.com.dio.coinconverter.databinding.ItemBinding


class AdapterHistory(): ListAdapter<ExChangeResponseValue, AdapterHistory.ViewHolderHistory>(DiffCallback()) {

   var list : MutableList<ExChangeResponseValue> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHistory {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemBinding.inflate( inflater, parent, false)
            return ViewHolderHistory( binding )
    }

    override fun onBindViewHolder(holder: ViewHolderHistory, position: Int) {
            holder.bind(getItem(position))
    }

    inner class ViewHolderHistory(binding: ItemBinding): RecyclerView.ViewHolder(binding.root){
        var binding = binding
        fun bind(exChangeResponseValue: ExChangeResponseValue){
                    binding.tvNomeMoeda.text = exChangeResponseValue.name
                    val coin = Coin.getCurrency(exChangeResponseValue.code)
                    binding.tvMoeda.text = exChangeResponseValue.bid.formatCurrency(coin.locale)
        }
    }

   class DiffCallback: DiffUtil.ItemCallback<ExChangeResponseValue>(){
       override fun areItemsTheSame( oldItem: ExChangeResponseValue,  newItem: ExChangeResponseValue) =  oldItem == newItem
       override fun areContentsTheSame( oldItem: ExChangeResponseValue, newItem: ExChangeResponseValue) = oldItem.id == newItem.id

   }

}