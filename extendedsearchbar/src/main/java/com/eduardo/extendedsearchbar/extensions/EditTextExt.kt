package com.eduardo.extendedsearchbar.extensions

import android.text.InputFilter
import android.widget.EditText

fun EditText.addFilter(filters: List<InputFilter>) {
    filters.forEach { this.addFilter(it) }
}

fun EditText.addFilter(filter: InputFilter) {
    filters =
        if (filters.isNullOrEmpty()) {
            arrayOf(filter)
        } else {
            filters
                .toMutableList()
                .apply {
                    removeAll { it.javaClass == filter.javaClass }
                    add(filter)
                }
                .toTypedArray()
        }
}