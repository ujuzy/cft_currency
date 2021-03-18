package com.cft.currency

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File


class MainActivity : AppCompatActivity() {
    private var _selectedCurrency = Currency()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val spinner = findViewById<Spinner>(R.id.spinner)
        val convertedTextView = findViewById<TextView>(R.id.converted)
        val input = findViewById<EditText>(R.id.input)
        val buttonConvert = findViewById<Button>(R.id.convert_button)
        val buttonRefresh = findViewById<Button>(R.id.refresh_button)

        val controller = Controller(this)

        if (File(filesDir, "currency.json").length().toInt() == 0) {
            GlobalScope.launch {
                controller.downloadJson("https://www.cbr-xml-daily.ru/daily_json.js")
            }
        } else {
            controller.serializeJson()
        }

        val currencies = controller.getCurrencies()
        val currenciesCodes = mutableListOf<String>()

        for (currency in currencies) {
            currenciesCodes.add(currency.CharCode)
        }

        recyclerView.adapter = CurrencyAdapter(this, currencies)
        val dividerItemDecoration = DividerItemDecoration(this, RecyclerView.VERTICAL)
        dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_drawable))
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.setHasFixedSize(true)

        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, currenciesCodes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                _selectedCurrency = currencies[position].copy()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        buttonConvert.setOnClickListener {
            val rubles = input.text.toString().toDouble()
            val converted = controller.convert(rubles, _selectedCurrency.CharCode).toString()

            convertedTextView.text = "$converted ${_selectedCurrency.CharCode}"
        }

        buttonRefresh.setOnClickListener {
            val file = File(filesDir, "currency.json")

            if (file.exists()) {
                file.delete()
            }

            if (File(filesDir, "currency.json").length().toInt() == 0) {
                GlobalScope.launch {
                    controller.downloadJson("https://www.cbr-xml-daily.ru/daily_json.js")
                }
            } else {
                controller.serializeJson()
            }
        }
    }
}