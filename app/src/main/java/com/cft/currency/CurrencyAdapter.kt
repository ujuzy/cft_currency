package com.cft.currency

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CurrencyAdapter (
    private val _context: Context,
    private val _dataset: List<Currency>
) : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder> () {

    class CurrencyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val charCode: TextView = view.findViewById(R.id.char_code)
        val value: TextView = view.findViewById(R.id.value)
        val name: TextView = view.findViewById(R.id.name)
        val nominal: TextView = view.findViewById(R.id.nominal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return CurrencyViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val charCodeText = "Код: " + _dataset[position].CharCode
        holder.charCode.text = charCodeText

        val valueText = "Стоимость: " + _dataset[position].Value.toString()
        holder.value.text = valueText

        val nameText = "Название: " + _dataset[position].Name
        holder.name.text = nameText

        val nominalText = "Номинал: " + _dataset[position].Nominal.toString()
        holder.nominal.text = nominalText
    }

    override fun getItemCount() = _dataset.size
}