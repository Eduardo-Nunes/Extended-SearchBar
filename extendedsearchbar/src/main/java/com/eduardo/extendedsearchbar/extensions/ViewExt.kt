package com.eduardo.extendedsearchbar.extensions

import android.view.View
import kotlin.math.roundToInt

fun View.convertDpToPx(dp: Int): Int = dp.times(context.resources.displayMetrics.density).roundToInt()
fun View.convertDpToRawPx(dp: Int): Float = dp.times(context.resources.displayMetrics.density)
