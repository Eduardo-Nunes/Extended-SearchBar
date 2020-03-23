package com.eduardo.extendedsearchbar.extensions

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.Toolbar
import com.eduardo.extendedsearchbar.R

private const val DEFAULT_INSET_MARGIN = 24

fun Toolbar.setUpSearchBar(
    queryHint: String,
    searchText: String = "",
    onFuzzyCallback: (String) -> Unit,
    onSearchCallback: ((String) -> Unit)? = null,
    insetMarginDp: Int = DEFAULT_INSET_MARGIN,
    @ColorRes hintColor: Int = R.color.grey_super_light,
    @DrawableRes backgroundResource: Int = R.drawable.round_t_white_16
) {
    contentInsetStartWithNavigation = 0
    setContentInsetsRelative(
        convertDpToPx(insetMarginDp),
        convertDpToPx(insetMarginDp)
    )
}

fun Toolbar.removeSearchBar() {
    contentInsetStartWithNavigation = 0
    setContentInsetsRelative(convertDpToPx(DEFAULT_INSET_MARGIN), 0)
}