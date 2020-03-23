package com.eduardo.extendedsearchbar.extensions

import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.Toolbar
import androidx.core.view.forEach
import com.eduardo.extendedsearchbar.view.ExtendedSearchBarView

private const val DEFAULT_INSET_MARGIN = 24

fun Toolbar.setUpSearchBar(
    queryHint: String,
    searchText: String = "",
    onFuzzyCallback: (String) -> Unit,
    onSearchCallback: ((String) -> Unit)? = null,
    insetMarginDp: Int = DEFAULT_INSET_MARGIN,
    @ColorRes hintColor: Int? = null,
    @DrawableRes backgroundResource: Int? = null
) {
    contentInsetStartWithNavigation = 0
    setContentInsetsRelative(
        convertDpToPx(insetMarginDp),
        convertDpToPx(insetMarginDp)
    )
    addView(ExtendedSearchBarView(context).apply {
        backgroundResource?.run(::changeSearchBackground)
        hintColor?.run(::changeHintColor)
        hintValue = queryHint
        searchValue = searchText
        setupInputCallback(onFuzzyCallback, onSearchCallback)
    }, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
}

fun Toolbar.removeSearchBar() {
    contentInsetStartWithNavigation = 0
    setContentInsetsRelative(convertDpToPx(DEFAULT_INSET_MARGIN), 0)

    forEach { view ->
        if (view is ExtendedSearchBarView) view.removeFromParent()
    }

}