package com.eduardo.extendedsearchbar.extensions

import java.text.Normalizer

fun String.normalize(): String =
    Regex("\\p{InCombiningDiacriticalMarks}+").replace(Normalizer.normalize(this.trim(), Normalizer.Form.NFD), "")
