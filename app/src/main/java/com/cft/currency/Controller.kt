package com.cft.currency

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.json.JSONObject
import java.net.URL

class Controller (private val _context: Context) {
    private val _currencies = mutableListOf<Currency>()

    fun serializeJson() {
        val json = _context.openFileInput("currency.json").bufferedReader().readText()

        val jsonCurrency = JSONObject(json).getJSONObject("Valute")
        val currencyCodes = jsonCurrency.keys()

        while (currencyCodes.hasNext()) {
            val code = currencyCodes.next().toString()

            val currencyInfo = jsonCurrency.getJSONObject(code).toString()

            _currencies.add(
                Json.decodeFromString(Currency.serializer(), currencyInfo)
            )
        }
    }

    fun downloadJson(url: String, isFileEmpty: Boolean = true) {
        val filename = "currency.json"
        _context.openFileOutput(filename, Context.MODE_PRIVATE).write(URL(url).readBytes())
    }

    fun getCurrencies() : List<Currency> = _currencies

    fun convert(rubles: Double, charCode: String) : Double {
        val anotherCurrency = _currencies.find { it.CharCode == charCode }!!
        val value = anotherCurrency.Value!! / anotherCurrency.Nominal!!

        return rubles / value
    }
}

