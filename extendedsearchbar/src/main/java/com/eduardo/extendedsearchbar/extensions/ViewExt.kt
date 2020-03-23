package com.eduardo.extendedsearchbar.extensions

import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import kotlin.math.roundToInt

fun View.convertDpToPx(dp: Int): Int =
    dp.times(context.resources.displayMetrics.density).roundToInt()

fun View.setMargins(
    start: Int = marginStart,
    top: Int = marginTop,
    end: Int = marginEnd,
    bottom: Int = marginBottom
) {
    val marginLayoutParams = (layoutParams as ViewGroup.MarginLayoutParams)
    marginLayoutParams.setMargins(start, top, end, bottom)
    post { layoutParams = marginLayoutParams }
}

fun View.removeFromParent() {
    (parent as ViewGroup).removeView(this)
}

fun View.setDimensions(x: Int = width, y: Int = height) {
    this.layoutParams.width = x
    this.layoutParams.height = y

    this.requestLayout()
}