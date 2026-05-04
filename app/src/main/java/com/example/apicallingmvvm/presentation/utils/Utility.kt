package com.example.apicallingmvvm.presentation.utils

import java.text.NumberFormat
import java.util.Locale

object Utility {
    fun formatToRupees(amount: Double): String {
        val format = NumberFormat.getCurrencyInstance(Locale("en", "IN"))
        return format.format(amount)
    }
}
