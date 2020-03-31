package com.eduardo.extendedsearchbar.extensions

import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.Toolbar
import androidx.core.view.children
import com.eduardo.extendedsearchbar.view.ExtendedSearchBarView

private const val DEFAULT_INSET_MARGIN = 24

fun Toolbar.addSearchBar(
    queryHint: String,
    searchText: String = String(),
    onFuzzySearchCallback: FuzzySearchCallback,
    insetMarginDp: Int = DEFAULT_INSET_MARGIN
) {
    contentInsetStartWithNavigation = 0
    setContentInsetsRelative(
        convertDpToPx(insetMarginDp),
        convertDpToPx(insetMarginDp)
    )
    addView(ExtendedSearchBarView(context).apply {
        hintValue = queryHint
        searchValue = searchText
        setupInputCallback(onFuzzySearchCallback)
    }, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
}

fun Toolbar.setupSearchBarRes(
    @ColorRes textColor: Int? = null,
    @DrawableRes iconCloseRes: Int? = null,
    @DrawableRes iconSearchRes: Int? = null,
    @DrawableRes backgroundResource: Int? = null
) =
    findSearchBarView()?.run {
        backgroundResource?.run(::changeSearchBackground)
        textColor?.run(::changeHintColor)
        textColor?.run(::changeSearchColor)
    }

fun Toolbar.findSearchBarView(): ExtendedSearchBarView? {
    return children.firstOrNull { view -> view is ExtendedSearchBarView } as? ExtendedSearchBarView
}

fun Toolbar.removeSearchBar() {
    contentInsetStartWithNavigation = 0
    setContentInsetsRelative(convertDpToPx(DEFAULT_INSET_MARGIN), 0)

    findSearchBarView()?.removeFromParent()
}